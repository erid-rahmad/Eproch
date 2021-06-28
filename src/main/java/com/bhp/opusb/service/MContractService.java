package com.bhp.opusb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.*;
import com.bhp.opusb.repository.*;
import com.bhp.opusb.service.dto.MContractDTO;
import com.bhp.opusb.service.dto.MContractLineDTO;
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

    public MContractService(ApplicationProperties properties, MContractDocumentService mContractDocumentService,
                            CDocumentTypeRepository cDocumentTypeRepository, MContractRepository mContractRepository,
                            MContractMapper mContractMapper) {
        this.mContractDocumentService = mContractDocumentService;
        this.cDocumentTypeRepository = cDocumentTypeRepository;
        this.mContractRepository = mContractRepository;
        this.mContractMapper = mContractMapper;
        document = properties.getDocuments().get("contract");
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

        log.info("this mcontact {}", mContract);

        return mContractMapper.toDto(mContract);
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
}
