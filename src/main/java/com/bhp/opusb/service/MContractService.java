package com.bhp.opusb.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.*;
import com.bhp.opusb.repository.*;
import com.bhp.opusb.service.dto.*;
import com.bhp.opusb.service.mapper.MContractLineMapper;
import com.bhp.opusb.service.mapper.MContractMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MContract}.
 */
@Service
@Transactional
public class MContractService {

    private final Logger log = LoggerFactory.getLogger(MContractService.class);

    private final MContractDocumentService mContractDocumentService;

    private final CDocumentTypeRepository cDocumentTypeRepository;

    private final MContractRepository mContractRepository;

    private final MContractMapper mContractMapper;


    private final Document document;
    private final MailService mailService;

    private final AdUserRepository adUserRepository;

    private final MContractTeamService mContractTeamService;
    private final MPurchaseOrderRepository mPurchaseOrderRepository;
    private final MPurchaseOrderService mPurchaseOrderService;
    private final MPurchaseOrderLineRepository mPurchaseOrderLineRepository;
    private final MPurchaseOrderLineService mPurchaseOrderLineService;

    private final AiMessageDispatcher messageDispatcher;

    public MContractService(ApplicationProperties properties, MContractDocumentService mContractDocumentService,
                            CDocumentTypeRepository cDocumentTypeRepository, MContractRepository mContractRepository,
                            MContractMapper mContractMapper, MailService mailService, AdUserRepository adUserRepository, MContractTeamService mContractTeamService, MPurchaseOrderRepository mPurchaseOrderRepository, MPurchaseOrderService mPurchaseOrderService, MPurchaseOrderLineRepository mPurchaseOrderLineRepository, MPurchaseOrderLineService mPurchaseOrderLineService,
                            AiMessageDispatcher messageDispatcher) {
        this.mContractDocumentService = mContractDocumentService;
        this.cDocumentTypeRepository = cDocumentTypeRepository;
        this.mContractRepository = mContractRepository;
        this.mContractMapper = mContractMapper;
        this.mailService = mailService;
        this.adUserRepository = adUserRepository;
        this.mContractTeamService = mContractTeamService;
        document = properties.getDocuments().get("contract");
        this.mPurchaseOrderRepository = mPurchaseOrderRepository;
        this.mPurchaseOrderService = mPurchaseOrderService;
        this.mPurchaseOrderLineRepository = mPurchaseOrderLineRepository;
        this.mPurchaseOrderLineService = mPurchaseOrderLineService;
        this.messageDispatcher = messageDispatcher;
    }

    @Autowired
    MContractLineService mContractLineService;

    @Autowired
    MContractLineRepository mContractLineRepository;

    @Autowired
    MContractLineMapper mContractLineMapper;

    @Autowired
    MBidNegoPriceRepository mBidNegoPriceRepository;

    @Autowired
    MBidNegoPriceLineRepository mBidNegoPriceLineRepository;


    /**
     * Save a mContract.
     *
     * @param mContractDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractDTO save(MContractDTO mContractDTO) {
        log.debug("Request to save MContract : {}", mContractDTO);
        MContract mContract = mContractMapper.toEntity(mContractDTO);

        final String docAction = mContract.getDocumentAction();
        final String docStatus = mContract.getDocumentStatus();


        if (mContract.getDocumentNo() == null) {
            mContract.setDocumentNo(DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mContractRepository));
        }

        if (mContract.getDocumentType() == null) {
            cDocumentTypeRepository.findFirstByName(document.getDocumentType())
                .ifPresent(mContract::setDocumentType);
        }

        if (!DocumentUtil.isTerminate(docStatus) && DocumentUtil.isTerminate(docAction)) {
            mContract.setDocumentStatus(docAction);
        }

        mContract = mContractRepository.save(mContract);

        if (mContractDTO.getLineDTOList() != null) {
            log.info("save Mcontract Line");
            MContract finalMContract = mContract;
            mContractDTO.getLineDTOList().forEach(mContractLineDTO -> {
                mContractLineDTO.setAdOrganizationId(finalMContract.getAdOrganization().getId());
                mContractLineDTO.setContractId(finalMContract.getId());
                mContractLineService.save(mContractLineDTO);
            });
        }

        MContractTeamDTO dto = mContractTeamService.findByContractId(mContract.getId());
        if (dto == null) {
            dto = new MContractTeamDTO();
            dto.setAdOrganizationId(mContract.getAdOrganization().getId());
            dto.setContractId(mContract.getId());
            dto.setStatus("U");

            mContractTeamService.save(dto);
        }

        return mContractMapper.toDto(mContract);
    }

    public void generateToPo(MContractToPoDTO mContractToPoDTO) {
        Set<Long> contract = new HashSet<>();
        mContractToPoDTO.getmContractLineDTOS().forEach(mContractLineDTO -> {
            if (!contract.contains(mContractLineDTO.getContractId())) {
                contract.add(mContractLineDTO.getContractId());
            }
        });

        contract.forEach(contract_ -> {
            Optional<MContract> mContract = mContractRepository.findById(contract_);
            MContractDTO mContractDTO = mContractMapper.toDto(mContract.get());
            MPurchaseOrderDTO mPurchaseOrderDTO = new MPurchaseOrderDTO();
            MPurchaseOrderDTO mPurchaseOrderDTO_ = new MPurchaseOrderDTO();

            mPurchaseOrderDTO.setAdOrganizationId(mContractDTO.getAdOrganizationId());
            mPurchaseOrderDTO.setDocumentTypeId(mContractToPoDTO.getDocumentTypeId());
            mPurchaseOrderDTO.setVendorId(mContractDTO.getVendorId());
            mPurchaseOrderDTO.setCurrencyId(mContractDTO.getCurrencyId());
            mPurchaseOrderDTO.setWarehouseId(mContractToPoDTO.getWarehouseId());
            mPurchaseOrderDTO.setCostCenterId(mContractDTO.getCostCenterId());
            mPurchaseOrderDTO.setPaymentTermId(mContractToPoDTO.getPaymentTermId());
            mPurchaseOrderDTO.setGrandTotal(mContractDTO.getPrice());

            mPurchaseOrderDTO_ = mPurchaseOrderService.save(mPurchaseOrderDTO);

            BigDecimal x = BigDecimal.ZERO;

            for (MContractLineDTO mContractLineDTO : mContractToPoDTO.getmContractLineDTOS()) {
                if (mContractLineDTO.getContractId() == contract_.longValue()) {
                    x = x.add(mContractLineDTO.getOrderAmount());
                    MPurchaseOrderLineDTO mPurchaseOrderLineDTO = new MPurchaseOrderLineDTO();

                    mPurchaseOrderLineDTO.setOrderAmount(mContractLineDTO.getOrderAmount());
                    mPurchaseOrderLineDTO.setQuantity(mContractLineDTO.getQuantityOrdered());
                    mPurchaseOrderLineDTO.setUnitPrice(mContractLineDTO.getCeilingPrice());

                    mPurchaseOrderLineDTO.setPurchaseOrderId(mPurchaseOrderDTO_.getId());
                    mPurchaseOrderLineDTO.setVendorId(mPurchaseOrderDTO_.getVendorId());
                    mPurchaseOrderLineDTO.setCostCenterId(mContractLineDTO.getCostCenterId());
                    mPurchaseOrderLineDTO.setAdOrganizationId(mContractLineDTO.getAdOrganizationId());
                    mPurchaseOrderLineDTO.setProductId(mContractLineDTO.getProductId());
                    mPurchaseOrderLineDTO.setUomId(mContractLineDTO.getUomId());

                    mPurchaseOrderLineService.save(mPurchaseOrderLineDTO);
                }
            }
            mPurchaseOrderDTO_.setGrandTotal(x);
            mPurchaseOrderService.save(mPurchaseOrderDTO_);
        });

    }

    public void generatebalance( MContractToPoDTO mContractToPoDTO){
        for(MContractLineDTO mContractLineDTO :  mContractToPoDTO.getmContractLineDTOS()){
            Optional<MContractLineDTO> mContractLineDTO1 = mContractLineService.findOne(mContractLineDTO.getId());
            log.info("this dataaa {} {} ",mContractLineDTO1,mContractLineDTO1.get().getContractNo());
            mContractLineDTO1.get().setQuantityBalance(mContractLineDTO.getQuantityBalance());
            mContractLineService.save(mContractLineDTO1.get());
        }

    }

    /**
     * Create a mContract from MVendorConfirmation.
     *
     * @param mContractDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractDTO generateFromVendorConfirmation(MContractDTO mContractDTO) {
        log.debug("Request to generate MContract from MVendorConfirmation : {}", mContractDTO);

        List<MContractLine> mContractLines = new ArrayList<>();
        List<MBidNegoPrice> mBidNegoPrice = mBidNegoPriceRepository.findbynegoline(mContractDTO.getNegoLineId());
        mContractDTO.setPrice(mBidNegoPrice.get(0).getNegotiationPrice());
        MContractDTO mContract = save(mContractDTO);
        List<MBidNegoPriceLine> mBidNegoPriceLine = mBidNegoPriceLineRepository.findbyHeader(mBidNegoPrice.get(0).getId());
        mBidNegoPriceLine.forEach(data -> {
            MContractLine mContractLine = new MContractLine()
                .product(data.getBiddingLine().getProduct())
                .quantity(data.getBiddingLine().getQuantity())
                .uom(data.getBiddingLine().getUom())
                .costCenter(data.getBiddingLine().getCostCenter())
                .ceilingPrice(data.getPriceNegotiation())
                .totalCeilingPrice(data.getTotalNegotiationPrice())
                .deliveryDate(data.getBiddingLine().getDeliveryDate())
                .remark(data.getBiddingLine().getRemark())
                .adOrganization(data.getBiddingLine().getAdOrganization())
                .contract(mContractMapper.toEntity(mContract))
                .active(true);
            mContractLines.add(mContractLine);
        });
        mContractLineRepository.saveAll(mContractLines);

        MContractTeamDTO dto = mContractTeamService.findByContractId(mContract.getId());
        if (dto == null) {
            dto = new MContractTeamDTO();
            dto.setAdOrganizationId(mContract.getAdOrganizationId());
            dto.setContractId(mContract.getId());
            dto.setStatus("U");

            mContractTeamService.save(dto);
        }

        return mContract;
    }


    /**
     * Get all the mContracts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContracts");
        return mContractRepository.findAll(pageable)
            .map(mContractMapper::toDto);
    }

    /**
     * Get one mContract by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractDTO> findOne(Long id) {
        log.debug("Request to get MContract : {}", id);
        return mContractRepository.findById(id)
            .map(mContractMapper::toDto);
    }

    /**
     * Delete the mContract by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContract : {}", id);
        mContractRepository.deleteById(id);
    }

//    @Scheduled(cron = "0 0 0 * * *",zone = "Indian/Maldives")
//    public void cron() {
//        log.info("this scheduler");
//        List<MContract> mContractTaskDTOS =mContractRepository.findAll();
//        mContractTaskDTOS.forEach(mContract -> {
//            LocalDate lt = LocalDate.now();
//            LocalDate lt_=mContract.getExpirationDate();
//            long daysBetween = ChronoUnit.DAYS.between(lt, lt_);
//            int x= (int) (daysBetween%mContract.getReminderSent());
//            if (daysBetween <= mContract.getEmailNotification() && daysBetween >=0 && x==0 ){
//                List<AdUser> adUsers= adUserRepository.findBycVendor(mContract.getVendor());
//                adUsers.forEach(adUser -> {
//                    mailService.sendEmail(adUser.getUser().getEmail(),
//                        "REMAINING", "REMAINING ABOUT CONTRACT ", false, true);
//                });
//            }
//        });
//    }
}
