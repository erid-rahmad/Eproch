package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.MRequisition;
import com.bhp.opusb.repository.CDocumentTypeRepository;
import com.bhp.opusb.repository.MRequisitionRepository;
import com.bhp.opusb.service.dto.MRequisitionDTO;
import com.bhp.opusb.service.dto.MRequisitionPayloadDTO;
import com.bhp.opusb.service.mapper.MRequisitionMapper;
import com.bhp.opusb.util.DocumentUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MRequisition}.
 */
@Service
@Transactional
public class MRequisitionService {

    private final Logger log = LoggerFactory.getLogger(MRequisitionService.class);

    private final ADOrganizationService adOrganizationService;
    private final MRequisitionLineService mRequisitionLineService;

    private final MRequisitionRepository mRequisitionRepository;
    private final CDocumentTypeRepository cDocumentTypeRepository;

    private final MRequisitionMapper mRequisitionMapper;
    private final Document document;

    @Autowired
    private ObjectMapper jsonMapper;

    private CDocumentType docType;

    public MRequisitionService(ApplicationProperties applicationProperties, ADOrganizationService adOrganizationService,
            MRequisitionLineService mRequisitionLineService, MRequisitionRepository mRequisitionRepository,
            CDocumentTypeRepository cDocumentTypeRepository, MRequisitionMapper mRequisitionMapper) {
        this.adOrganizationService = adOrganizationService;
        this.mRequisitionLineService = mRequisitionLineService;
        this.mRequisitionRepository = mRequisitionRepository;
        this.cDocumentTypeRepository = cDocumentTypeRepository;
        this.mRequisitionMapper = mRequisitionMapper;

        document = applicationProperties.getDocuments().get("purchaseRequisition");
    }

    /**
     * Save a mRequisition.
     *
     * @param mRequisitionDTO the entity to save.
     * @return the persisted entity.
     */
    public MRequisitionDTO save(MRequisitionDTO mRequisitionDTO) {
        log.debug("Request to save MRequisition : {}", mRequisitionDTO);
        MRequisition mRequisition = mRequisitionMapper.toEntity(mRequisitionDTO);

        if (mRequisition.getDocumentNo() == null) {
            mRequisition.documentNo(DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mRequisitionRepository));
        }

        if (mRequisition.getDocumentType() == null) {
            cDocumentTypeRepository.findFirstByName(document.getDocumentType())
                .ifPresent(mRequisition::setDocumentType);
        }

        if (mRequisition.getAdOrganization() == null) {
            mRequisition.setAdOrganization(adOrganizationService.getDefaultOrganization());
        }

        mRequisition = mRequisitionRepository.save(mRequisition);
        mRequisitionLineService.saveAll(mRequisitionDTO.getMRequisitionLines(), mRequisition, mRequisition.getAdOrganization());
        
        return mRequisitionMapper.toDto(mRequisition);
    }

    /**
     * Get all the mRequisitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MRequisitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MRequisitions");
        return mRequisitionRepository.findAll(pageable)
            .map(mRequisitionMapper::toDto);
    }

    /**
     * Get one mRequisition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MRequisitionDTO> findOne(Long id) {
        log.debug("Request to get MRequisition : {}", id);
        return mRequisitionRepository.findById(id)
            .map(mRequisitionMapper::toDto);
    }

    /**
     * Delete the mRequisition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MRequisition : {}", id);
        mRequisitionRepository.deleteById(id);
    }

    /**
     * TODO Use a generic method to update the document status for every entities.
     * TODO Use the workflow engine for maintaining the flow state.
     */
    public void updateDocumentStatus(MRequisitionDTO mRequisitionDTO) {
        log.debug("Request to update MRequisition's document status : {}", mRequisitionDTO);
        MRequisition mRequisition = mRequisitionMapper.toEntity(mRequisitionDTO);
        String action = mRequisition.getDocumentAction();
        String status = mRequisition.getDocumentStatus();
        boolean approved = false;
        boolean processed = false;

        if (DocumentUtil.isApprove(mRequisition.getDocumentStatus())) {
            approved = true;
            processed = true;
        } else if (DocumentUtil.isReject(mRequisition.getDocumentStatus())) {
            processed = true;
        }

        mRequisitionRepository.updateDocumentStatus(mRequisition.getId(), action, status, approved, processed);
    }

    public MRequisitionDTO synchronize(String input) throws JsonProcessingException {
        log.debug("Request to synchronize MRequisition : {}", input);
        MRequisitionPayloadDTO payload = jsonMapper.readValue(input, MRequisitionPayloadDTO.class);

        String documentNo = payload.getDocumentNo();
        List<MRequisition> requis = mRequisitionRepository.findByDocumentNo(documentNo);

        MRequisitionDTO newReq;

        if(requis.size()>0){
            newReq = mRequisitionMapper.toDto(requis.get(0));
        } else {
            newReq = new MRequisitionDTO();
            newReq.setDateTrx(payload.getDateTrx());
            newReq.setDocumentNo(documentNo);
            newReq.setDescription(payload.getDescription());
            newReq.setDocumentStatus("APV");
            newReq.setDocumentAction("APV");
            newReq.setApproved(true);
            newReq.setProcessed(true);
            newReq.setDocumentTypeId(docType.getId());
            newReq.setId(payload.getRequisitionHeaderId());

            ADOrganization org = adOrganizationService.findOrCreate(payload.getOrganizationCode().toString(), payload.getOrganizationName());
            newReq.setAdOrganizationId(org.getId());

            MRequisition req = mRequisitionMapper.toEntity(newReq);
            mRequisitionRepository.save(req);
        }

        return newReq;
    }

    @PostConstruct
    private void postConstruct(){
        docType = cDocumentTypeRepository.findFirstByName(document.getDocumentType()).orElse(new CDocumentType());
    }
}
