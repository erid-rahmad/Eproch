package com.bhp.opusb.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.sql.DataSource;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.repository.MVerificationRepository;
import com.bhp.opusb.service.dto.MMatchPODTO;
import com.bhp.opusb.service.dto.MVerificationDTO;
import com.bhp.opusb.service.dto.MVerificationLineCriteria;
import com.bhp.opusb.service.dto.MVerificationLineDTO;
import com.bhp.opusb.service.dto.PaymentStatusDTO;
import com.bhp.opusb.service.dto.VerificationDTO;
import com.bhp.opusb.service.mapper.MVerificationMapper;
import com.bhp.opusb.service.trigger.process.integration.MVerificationMessageDispatcher;
import com.bhp.opusb.util.DocumentUtil;
import com.bhp.opusb.web.websocket.DashboardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import io.github.jhipster.service.filter.LongFilter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Service Implementation for managing {@link MVerification}.
 */
@Service
@Transactional
public class MVerificationService {

    private final Logger log = LoggerFactory.getLogger(MVerificationService.class);
    private final DataSource dataSource;

    private final ADOrganization organization;
    private final MMatchPOService mMatchPOService;
    private final MVerificationRepository mVerificationRepository;
    private final MVerificationMapper mVerificationMapper;
    private final MVerificationLineService mVerificationLineService;
    private final MVerificationLineQueryService mVerificationLineQueryService;
    private final AiMessageDispatcher messageDispatcher;
    private final UserService userService;
    private final ADOrganizationService adOrganizationService;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public MVerificationService(DataSource dataSource, MMatchPOService mMatchPOService,
            MVerificationRepository mVerificationRepository, MVerificationMapper mVerificationMapper,
            MVerificationLineService mVerificationLineService,
            MVerificationLineQueryService mVerificationLineQueryService, ADOrganizationService adOrganizationService,
            AiMessageDispatcher messageDispatcher, UserService userService) {
        this.dataSource = dataSource;
        this.mMatchPOService = mMatchPOService;
        this.mVerificationRepository = mVerificationRepository;
        this.mVerificationMapper = mVerificationMapper;
        this.mVerificationLineService = mVerificationLineService;
        this.mVerificationLineQueryService = mVerificationLineQueryService;
        this.messageDispatcher = messageDispatcher;
        this.userService = userService;
        this.adOrganizationService = adOrganizationService;

        organization = adOrganizationService.getDefaultOrganization();
    }

    public JasperPrint exportVerification(Long verificationId, String key) throws IOException, SQLException, JRException {

        File file = null;

        if (key.equals("invoice-verification")) {
            file = ResourceUtils.getFile("classpath:templates/report/invoice-verification.jrxml");
        } else if (key.equals("summary-invoice-verification")) {
            file = ResourceUtils.getFile("classpath:templates/report/summary-invoice-verification.jrxml");
        } else if (key.equals("invoice-verification-receipt")) {
            file = ResourceUtils.getFile("classpath:templates/report/invoice-verification-receipt.jrxml");
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("verificationId", verificationId);
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

    }

    /**
     * Save the invoice verification document as DRAFT, including its lines.
     * @param verificationDTO
     * @return
     */
    public MVerificationDTO saveDocument(VerificationDTO verificationDTO) {
        log.debug("Request to save MVerification document : {}", verificationDTO);
        final MVerification verification = mVerificationMapper.toEntity(verificationDTO.getForm());
        final String documentStatus = verification.getVerificationStatus();
        final boolean approval = DocumentUtil.isApprove(documentStatus);
        final boolean rejection = DocumentUtil.isReject(documentStatus);
        String eventName = null;
        
        if (DocumentUtil.isNew(documentStatus)) {
            eventName = "INVOICE_CREATED";
            verification.active(true)
                .adOrganization(organization)
                .dateTrx(verification.getInvoiceDate())
                .documentNo(DocumentUtil.buildRunningNumber(mVerificationRepository))
                .documentAction(DocumentUtil.STATUS_SUBMIT)
                .documentStatus(DocumentUtil.STATUS_DRAFT)
                .verificationNo(verification.getDocumentNo())
                .verificationStatus(DocumentUtil.STATUS_DRAFT);
        } else if (approval) {
            eventName = "INVOICE_APPROVED";
            verification.dateApprove(LocalDate.now())
                .payStatus("U")
                .documentAction(DocumentUtil.STATUS_APPROVE)
                .documentStatus(DocumentUtil.STATUS_APPROVE)
                .approved(true)
                .processed(true);
        } else if (rejection) {
            eventName = "INVOICE_REJECTED";
            verification.dateReject(LocalDate.now())
                .documentAction(DocumentUtil.STATUS_REJECT)
                .documentStatus(DocumentUtil.STATUS_REJECT)
                .approved(false)
                .processed(true);
        }

        // Validate each line againts its receipt line, only if it is not the rejection process.
        List<MMatchPODTO> mMatchPODTOs = new ArrayList<>(verificationDTO.getLines().size());
        for (MVerificationLineDTO lineDTO : verificationDTO.getLines()) {
            MMatchPODTO mMatchPODTO = mMatchPOService.findByKeys(lineDTO.getAdOrganizationCode(), lineDTO.getcDocType(),
                lineDTO.getPoNo(), lineDTO.getReceiveNo(), lineDTO.getLineNoPo(), lineDTO.getLineNoMr(),
                lineDTO.getOrderSuffix()).orElse(null);

            if (mMatchPODTO == null) {
                throw new PoReceiptReversedException(lineDTO);
            }

            // Marks match PO as "invoiced" once the document moved to draft.
            if (Boolean.FALSE.equals(mMatchPODTO.isInvoiced())) {
                log.debug("Mark match PO as invoiced: {}", mMatchPODTO);
                mMatchPODTO.setInvoiced(true);
                mMatchPODTOs.add(mMatchPODTO);
            }
        }

        mMatchPOService.saveAll(mMatchPODTOs);
        mVerificationRepository.save(verification.receiptReversed(false));
        mVerificationLineService.saveAll(verificationDTO.getLines(), verification, organization);

        if (! verificationDTO.getRemovedLines().isEmpty()) {
            mVerificationLineService.removeAll(verificationDTO.getRemovedLines());
        }

        if (approval || rejection) {
            sendDocument(verification);
        }

        if (eventName != null) {
            messagingTemplate.convertAndSend(DashboardService.TOPIC_DASHBOARD, eventName);
        }
        return mVerificationMapper.toDto(verification);
    }

    public void sendDocument(MVerification verification) {
        final String documentStatus = verification.getVerificationStatus();

        findOne(verification.getId())
            .ifPresent(header -> {
                MVerificationLineCriteria lineCriteria = new MVerificationLineCriteria();
                LongFilter idFilter = new LongFilter();

                idFilter.setEquals(header.getId());
                lineCriteria.setVerificationId(idFilter);
                List<MVerificationLineDTO> lines = mVerificationLineQueryService.findByCriteria(lineCriteria);

                if (DocumentUtil.isApprove(documentStatus) && ! lines.isEmpty()) {
                    // Dispatch the header to the external system.
                    final Map<String, Object> headerPayload = new HashMap<>(2);
                    headerPayload.put(MVerificationMessageDispatcher.KEY_CONTEXT, MVerificationMessageDispatcher.CONTEXT_HEADER);
                    headerPayload.put(MVerificationMessageDispatcher.KEY_PAYLOAD, header);
                    messageDispatcher.dispatch(MVerificationMessageDispatcher.BEAN_NAME, headerPayload);

                    // Dispatch the lines to the external system.
                    if (!lines.isEmpty()) {
                        final Map<String, Object> linesPayload = new HashMap<>(2);
                        linesPayload.put(MVerificationMessageDispatcher.KEY_CONTEXT, MVerificationMessageDispatcher.CONTEXT_LINES);
                        linesPayload.put(MVerificationMessageDispatcher.KEY_PAYLOAD, lines);
                        messageDispatcher.dispatch(MVerificationMessageDispatcher.BEAN_NAME, linesPayload);
                    }
                } else if(DocumentUtil.isReject(documentStatus)) {
                    userService.sendNotifRejectVerification(header, lines);
                }
            });
    }

    /**
     * Save a mVerification.
     *
     * @param mVerificationDTO the entity to save.
     * @return the persisted entity.
     */
    public MVerificationDTO save(MVerificationDTO mVerificationDTO) {
        log.debug("Request to save MVerification : {}", mVerificationDTO);
        MVerification mVerification = mVerificationMapper.toEntity(mVerificationDTO);
        final String documentStatus = mVerification.getVerificationStatus();
        String eventName = null;

        mVerification.documentStatus(documentStatus).documentAction(documentStatus);

        // Just update the submission date if required.
        if (DocumentUtil.isSubmit(documentStatus)) {
            eventName = "INVOICE_SUBMITTED";
            List<MVerificationLineDTO> lines = mVerificationLineQueryService.findByHeader(mVerification);

            // Validate each line againts its receipt line.
            for (MVerificationLineDTO lineDTO : lines) {
                if (!mMatchPOService.isValidMatchPO(lineDTO.getAdOrganizationCode(), lineDTO.getcDocType(),
                        lineDTO.getPoNo(), lineDTO.getReceiveNo(), lineDTO.getLineNoPo(), lineDTO.getLineNoMr(),
                        lineDTO.getOrderSuffix())) {
                    throw new PoReceiptReversedException(lineDTO);
                }
            }
            mVerification.setDescription(null);

            if (mVerification.getDateSubmit() == null || mVerification.getDateReject() != null) {
                mVerification.setDateSubmit(LocalDate.now());
            }
        } else if (DocumentUtil.isVoid(documentStatus)) {
            eventName = "INVOICE_VOIDED";
            mVerificationLineQueryService.findByHeader(mVerification).forEach(line -> {
                mMatchPOService.openMatchPO(line.getAdOrganizationCode(), line.getcDocType(), line.getPoNo(),
                        line.getReceiveNo(), line.getLineNoPo(), line.getLineNoMr(), line.getOrderSuffix());
            });
        } else if (DocumentUtil.isReopen(documentStatus)) {
            eventName = "INVOICE_REOPENED";
        }

        mVerification = mVerificationRepository.save(mVerification.receiptReversed(false));
        if (eventName != null) {
            messagingTemplate.convertAndSend(DashboardService.TOPIC_DASHBOARD, eventName);
        }
        return mVerificationMapper.toDto(mVerification);
    }

    /**
     * Update the following fields:
     * invoice_ap: payload.VHDOC
     * doc_type: payload.VHDCT
     * pay_status: payload.VHPST
     * pay_amt: payload.VHAA
     * pay_date: payload.VHDMTJ.getTarget().isNull() ? null \: T(java.time.LocalDate).parse(payload.VHDMTJ.getTarget().textValue())
     * last_modified_by: payload.VHPTUPUSER.getTarget().isNull() ? null \: payload.VHPTUPUSER.getTarget().textValue().trim()
     */
    public MVerificationDTO synchronize(String message) throws JsonProcessingException {
        log.debug("Request to synchronize MVerification : {}", message);

        final PaymentStatusDTO payload = jsonMapper.readValue(message, PaymentStatusDTO.class);
        Optional<MVerification> record = mVerificationRepository.findFirstByVerificationNo(payload.getVerificationNo());

        if (record.isPresent()) {
            MVerification mVerification = record.get();
            String currentStatus = mVerification.getPayStatus();
            String incomingStatus = payload.getStatus();

            if (incomingStatus.equals(PaymentStatusDTO.STATUS_APPROVED)
                    && (currentStatus == null || PaymentStatusDTO.STATUS_UNPROCESSED.equals(currentStatus))) {

                log.debug("Set invoice status to {}", incomingStatus);
                final ADOrganization org = adOrganizationService.findOrCreate(payload.getOrgCode());
                mVerification.payStatus(incomingStatus)
                    .invoiceAp(payload.getDocumentNo()) // Voucher No.
                    .docType(payload.getDocumentType())
                    .adOrganization(org);
            } else if (incomingStatus.equals(PaymentStatusDTO.STATUS_PAID)
                    && ! PaymentStatusDTO.STATUS_PAID.equals(currentStatus)) {
                final String updatedBy = payload.getUpdatedBy();

                log.debug("Set invoice status to {}", incomingStatus);
                final ADOrganization org = adOrganizationService.findOrCreate(payload.getOrgCode());
                mVerification.payStatus(incomingStatus)
                    .invoiceAp(payload.getDocumentNo()) // Voucher No.
                    .docType(payload.getDocumentType())
                    .payAmt(payload.getAmount())
                    .payDate(payload.getDate())
                    .adOrganization(org);
                
                if (updatedBy != null && ! updatedBy.isEmpty()) {
                    mVerification.setLastModifiedBy(updatedBy.trim());
                }
            } else if ( ! Objects.equals(mVerification.getInvoiceAp(), payload.getDocumentNo())) {
                log.debug("Update voucher number from {} to {}", mVerification.getInvoiceAp(), payload.getDocumentNo());
                final ADOrganization org = adOrganizationService.findOrCreate(payload.getOrgCode());
                mVerification.invoiceAp(payload.getDocumentNo())
                    .docType(payload.getDocumentType())
                    .adOrganization(org);
            }

            return mVerificationMapper.toDto(mVerification);
        }

        return null;
    }

    /**
     * Get all the mVerifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVerificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVerifications");
        return mVerificationRepository.findAll(pageable)
            .map(mVerificationMapper::toDto);
    }

    /**
     * Get one mVerification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVerificationDTO> findOne(Long id) {
        log.debug("Request to get MVerification : {}", id);
        return mVerificationRepository.findById(id)
            .map(mVerificationMapper::toDto);
    }

    /**
     * Delete the mVerification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVerification : {}", id);
        mVerificationRepository.deleteById(id);
    }
}
