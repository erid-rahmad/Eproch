package com.bhp.opusb.service;

import java.util.Optional;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.MVendorEvaluation;
import com.bhp.opusb.repository.MVendorEvaluationRepository;
import com.bhp.opusb.service.dto.MVendorEvaluationDTO;
import com.bhp.opusb.service.mapper.MVendorEvaluationMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MVendorEvaluation}.
 */
@Service
@Transactional
public class MVendorEvaluationService {

    private final Logger log = LoggerFactory.getLogger(MVendorEvaluationService.class);

    private final MVendorEvaluationRepository mVendorEvaluationRepository;

    private final MVendorEvaluationMapper mVendorEvaluationMapper;

    private final Document document;

    public MVendorEvaluationService(ApplicationProperties properties,
            MVendorEvaluationRepository mVendorEvaluationRepository, MVendorEvaluationMapper mVendorEvaluationMapper) {
        this.mVendorEvaluationRepository = mVendorEvaluationRepository;
        this.mVendorEvaluationMapper = mVendorEvaluationMapper;
        this.document = properties.getDocuments().get("vendorEvaluation");
    }

    /**
     * Save a mVendorEvaluation.
     *
     * @param mVendorEvaluationDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorEvaluationDTO save(MVendorEvaluationDTO mVendorEvaluationDTO) {
        log.debug("Request to save MVendorEvaluation : {}", mVendorEvaluationDTO);
        MVendorEvaluation mVendorEvaluation = mVendorEvaluationMapper.toEntity(mVendorEvaluationDTO);

        if (mVendorEvaluation.getDocumentNo() == null) {
            mVendorEvaluation.setDocumentNo(DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mVendorEvaluationRepository));
        }

        mVendorEvaluation = mVendorEvaluationRepository.save(mVendorEvaluation);
        return mVendorEvaluationMapper.toDto(mVendorEvaluation);
    }

    /**
     * Get all the mVendorEvaluations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorEvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorEvaluations");
        return mVendorEvaluationRepository.findAll(pageable)
            .map(mVendorEvaluationMapper::toDto);
    }

    /**
     * Get one mVendorEvaluation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorEvaluationDTO> findOne(Long id) {
        log.debug("Request to get MVendorEvaluation : {}", id);
        return mVendorEvaluationRepository.findById(id)
            .map(mVendorEvaluationMapper::toDto);
    }

    /**
     * Delete the mVendorEvaluation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorEvaluation : {}", id);
        mVendorEvaluationRepository.deleteById(id);
    }
}
