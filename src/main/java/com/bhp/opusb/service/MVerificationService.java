package com.bhp.opusb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.repository.MVerificationRepository;
import com.bhp.opusb.service.dto.MVerificationDTO;
import com.bhp.opusb.service.dto.MVerificationLineCriteria;
import com.bhp.opusb.service.dto.MVerificationLineDTO;
import com.bhp.opusb.service.dto.VerificationDTO;
import com.bhp.opusb.service.mapper.MVerificationMapper;
import com.bhp.opusb.service.trigger.process.integration.MVerificationOutbound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.filter.LongFilter;

/**
 * Service Implementation for managing {@link MVerification}.
 */
@Service
@Transactional
public class MVerificationService {

    private final Logger log = LoggerFactory.getLogger(MVerificationService.class);

    private final ADOrganization organization;
    private final MVerificationRepository mVerificationRepository;
    private final MVerificationMapper mVerificationMapper;
    private final MVerificationLineService mVerificationLineService;
    private final MVerificationLineQueryService mVerificationLineQueryService;
    private final MVerificationOutbound mVerificationOutbound;

    public MVerificationService(MVerificationRepository mVerificationRepository,
            MVerificationMapper mVerificationMapper, MVerificationLineService mVerificationLineService,
            MVerificationLineQueryService mVerificationLineQueryService, ADOrganizationService adOrganizationService,
            MVerificationOutbound mVerificationOutbound) {
        this.mVerificationRepository = mVerificationRepository;
        this.mVerificationMapper = mVerificationMapper;
        this.mVerificationLineService = mVerificationLineService;
        this.mVerificationLineQueryService = mVerificationLineQueryService;
        this.mVerificationOutbound = mVerificationOutbound;

        organization = adOrganizationService.getDefaultOrganization();
    }

    public MVerification submitEVerification(VerificationDTO verificationDTO) {
        // Ensure verification has generated ID.
        MVerification verification = mVerificationMapper.toEntity(verificationDTO.getForm());
        verification.active(true)
            .adOrganization(organization);

        mVerificationRepository.save(verification);

        // Batch save verification line.
        if (verificationDTO.getRemove().isEmpty()) {
            mVerificationLineService.saveAll(verificationDTO.getLine(), verification, organization);
        } else {
            mVerificationLineService.removeAll(verificationDTO.getRemove());
        }

        return verification;
    }

    /**
     * TODO Use a generic method to update the document status for every entities.
     * TODO Use the workflow engine for maintaining the flow state.
     */
    public void updateDocumentStatus(MVerificationDTO mVerificationDTO) {
        log.debug("Request to update MVerificationDTO's document status : {}", mVerificationDTO);
        MVerification mVerification = mVerificationMapper.toEntity(mVerificationDTO);

        mVerificationRepository.save(mVerification);
        
        if (mVerification.getVerificationStatus().equals("APV")) {
            final Map<String, Object> headerParams = new HashMap<>(2);
            headerParams.put("context", MVerificationOutbound.CONTEXT_HEADER);
            headerParams.put("payload", mVerificationMapper.toDto(mVerification));
            mVerificationOutbound.run(headerParams);

            MVerificationLineCriteria lineCriteria = new MVerificationLineCriteria();
            LongFilter idFilter = new LongFilter();
            idFilter.setEquals(mVerification.getId());
            lineCriteria.setVerificationId(idFilter);
            List<MVerificationLineDTO> lines = mVerificationLineQueryService.findByCriteria(lineCriteria);
            
            if (!lines.isEmpty()) {
                final Map<String, Object> lineParams = new HashMap<>(2);
                lineParams.put("context", MVerificationOutbound.CONTEXT_LINES);
                lineParams.put("payload", lines);
                mVerificationOutbound.run(lineParams);
            }
        }
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
        mVerification = mVerificationRepository.save(mVerification);
        return mVerificationMapper.toDto(mVerification);
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
