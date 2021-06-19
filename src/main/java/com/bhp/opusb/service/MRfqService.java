package com.bhp.opusb.service;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.MRequisitionLine;
import com.bhp.opusb.domain.MRfq;
import com.bhp.opusb.domain.MRfqLine;
import com.bhp.opusb.repository.CDocumentTypeRepository;
import com.bhp.opusb.repository.MRequisitionLineRepository;
import com.bhp.opusb.repository.MRequisitionRepository;
import com.bhp.opusb.repository.MRfqLineRepository;
import com.bhp.opusb.repository.MRfqRepository;
import com.bhp.opusb.service.dto.MRequisitionLineDTO;
import com.bhp.opusb.service.dto.MRfqDTO;
import com.bhp.opusb.service.mapper.MRequisitionLineMapper;
import com.bhp.opusb.service.mapper.MRfqMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Implementation for managing {@link MRfq}.
 */
@Service
@Transactional
public class MRfqService {

    private final Logger log = LoggerFactory.getLogger(MRfqService.class);

    private final ADOrganizationService adOrganizationService;
    private final CDocumentTypeRepository cDocumentTypeRepository;

    private final MRfqRepository mRfqRepository;
    private final MRfqMapper mRfqMapper;

    private final MRfqLineRepository mRfqLineRepository;
    private final MRequisitionRepository mRequisitionRepository;

    private final MRequisitionLineRepository mRequisitionLineRepository;
    private final MRequisitionLineMapper mRequisitionLineMapper;

    private final Document document;

    public MRfqService(ApplicationProperties applicationProperties, MRfqRepository mRfqRepository, MRfqMapper mRfqMapper,
    ADOrganizationService adOrganizationService, CDocumentTypeRepository cDocumentTypeRepository,
    MRequisitionLineMapper mRequisitionLineMapper, MRfqLineRepository mRfqLineRepository,
    MRequisitionRepository mRequisitionRepository, MRequisitionLineRepository mRequisitionLineRepository) {
        this.mRfqRepository = mRfqRepository;
        this.mRfqMapper = mRfqMapper;
        this.adOrganizationService=adOrganizationService;
        this.cDocumentTypeRepository=cDocumentTypeRepository;
        this.mRequisitionLineMapper = mRequisitionLineMapper;
        this.mRfqLineRepository = mRfqLineRepository;
        this.mRequisitionRepository = mRequisitionRepository;
        this.mRequisitionLineRepository = mRequisitionLineRepository;
        
        document = applicationProperties.getDocuments().get("quotation");
    }

    /**
     * Save a mRfq.
     *
     * @param mRfqDTO the entity to save.
     * @return the persisted entity.
     */
    public MRfqDTO save(MRfqDTO mRfqDTO) {
        log.debug("Request to save MRfq : {}", mRfqDTO);
        MRfq mRfq = mRfqMapper.toEntity(mRfqDTO);

        if (mRfq.getDocumentNo() == null) {
            mRfq.documentNo(initDocumentNumber());
        }

        if (mRfq.getDocumentType() == null) {
            cDocumentTypeRepository.findFirstByName(document.getDocumentType())
                .ifPresent(mRfq::setDocumentType);
        }

        if (mRfq.getAdOrganization() == null) {
            mRfq.setAdOrganization(adOrganizationService.getDefaultOrganization());
        }

        mRfq = mRfqRepository.save(mRfq);
        return mRfqMapper.toDto(mRfq);
    }

    /**
     * Get all the mRfqs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MRfqDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MRfqs");
        return mRfqRepository.findAll(pageable)
            .map(mRfqMapper::toDto);
    }

    /**
     * Get one mRfq by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MRfqDTO> findOne(Long id) {
        log.debug("Request to get MRfq : {}", id);
        return mRfqRepository.findById(id)
            .map(mRfqMapper::toDto);
    }

    /**
     * Delete the mRfq by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MRfq : {}", id);
        mRfqRepository.deleteById(id);
    }

    /**
     * Generate mPurchaseOrders from requisition.
     *
     * @param mPurchaseOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public List<MRfqDTO> saveFromRequisition(MRfqDTO mRfqDTO) {
        log.debug("Request to generate MRfq : {}", mRfqDTO);
        
        // Map of vendorId and Rfq entity.
        // Temporarily map the Rfq entity for each vendor.
        Map<Long, MRfq> quotations = new HashMap<>();
        List<MRequisitionLineDTO> requisitionLines = mRfqDTO.getRequisitionLines();
        List<MRfqLine> rfqLines = new ArrayList<>(requisitionLines.size());

        for (MRequisitionLineDTO mRequisitionLineDTO : requisitionLines) {
            MRequisitionLine mRequisitionLine = mRequisitionLineMapper.toEntity(mRequisitionLineDTO);
            
            MRfq rfq = quotations.computeIfAbsent(mRequisitionLineDTO.getVendorId(),
                    key -> initQuotation(mRfqDTO, mRequisitionLine));

            MRfqLine mRfqLine = initPurchaseOrderLine(rfq, mRequisitionLine);
            //final BigDecimal grandTotal = mPurchaseOrder.getGrandTotal().add(mPurchaseOrderLine.getOrderAmount());

            //mPurchaseOrder.setGrandTotal(grandTotal);
            rfqLines.add(mRfqLine);
        }
        
        List<MRfqDTO> result = mRfqMapper.toDto(mRfqRepository.saveAll(quotations.values()));
        mRfqLineRepository.saveAll(rfqLines);
        mRequisitionLineRepository.saveAll(mRequisitionLineMapper.toEntity(requisitionLines));
        return result;
    }

    /**
     * First initialization of MPurchaseOrder entity.
     * @param mRfqDTO
     * @param mRequisitionLine
     * @return
     */
    private MRfq initQuotation(MRfqDTO mRfqDTO, MRequisitionLine mRequisitionLine) {
        MRfq mRfq = mRfqMapper.toEntity(mRfqDTO);
        //Optional<CVendorTax> vendorTaxInfo = cVendorTaxRepository.findFirstByVendor(mRequisitionLine.getVendor());
        //boolean taxable = false;

        /*
        if (vendorTaxInfo.isPresent()) {
            taxable = Boolean.TRUE.equals(vendorTaxInfo.get().isTaxableEmployers());
        }
        */
        // Set the default Purchase Order document type.
        if (mRfq.getDocumentType() == null) {
            cDocumentTypeRepository.findFirstByName(document.getDocumentType())
                .ifPresent(mRfq::setDocumentType);
        }

        // Set the requestor of the purchase order.
        mRequisitionRepository.findById(mRequisitionLine.getRequisition().getId())
                .ifPresent(mRequisition
                    -> mRfq
                        .costCenter(mRequisition.getCostCenter())
                        .businessCategory(mRfq.getBusinessClassification())
                        .businessClassification(mRfq.getBusinessClassification())
                        .adOrganization(mRfq.getAdOrganization())
                        .warehouse(mRequisition.getWarehouse())
                        .documentAction("SMT").documentStatus("DRF")
                        .setCreatedBy(mRequisition.getCreatedBy()));

        return mRfq.active(true)
            .documentNo(initDocumentNumber());
            //.grandTotal(new BigDecimal(0))
            //.tax(taxable)
            //.vendor(mRequisitionLine.getVendor());
    }

    private String initDocumentNumber() {
        return DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mRfqRepository);
    }

    private MRfqLine initPurchaseOrderLine(MRfq mPurchaseOrder, MRequisitionLine mRequisitionLine) {
        final MRfqLine mPurchaseOrderLine = new MRfqLine();
        //final BigDecimal orderAmount = mRequisitionLine.getQuantityOrdered().multiply(mRequisitionLine.getUnitPrice());

        mPurchaseOrderLine.active(true)
            .adOrganization(mPurchaseOrder.getAdOrganization())
            .businessCategory(mPurchaseOrder.getBusinessCategory())
            .documentDate(mPurchaseOrder.getDateTrx())
            .dateRequired(mPurchaseOrder.getDateRequired())
            .documentAction("SMT")
            .documentStatus("DRF")
            .product(mRequisitionLine.getProduct())
            .quotation(mPurchaseOrder)
            .releaseQty(mRequisitionLine.getQuantityOrdered().intValue())
            .remark(mRequisitionLine.getRemark())
            //.requisition(mRequisitionLine.getRequisition())
            //.unitPrice(mRequisitionLine.getUnitPrice())
            .uom(mRequisitionLine.getUom());
            //.vendor(mRequisitionLine.getVendor())
            //.warehouse(mPurchaseOrder.getWarehouse());

        return mPurchaseOrderLine;
    }
}
