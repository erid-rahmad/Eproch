package com.bhp.opusb.service;

import com.bhp.opusb.domain.MTechProposalEvaluation;
import com.bhp.opusb.repository.MTechProposalEvaluationRepository;
import com.bhp.opusb.service.dto.MTechProposalEvaluationDTO;
import com.bhp.opusb.service.mapper.MTechProposalEvaluationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MTechProposalEvaluation}.
 */
@Service
@Transactional
public class MTechProposalEvaluationService {

    private final Logger log = LoggerFactory.getLogger(MTechProposalEvaluationService.class);

    private final MTechProposalEvaluationRepository mTechProposalEvaluationRepository;

    private final MTechProposalEvaluationMapper mTechProposalEvaluationMapper;

    public MTechProposalEvaluationService(MTechProposalEvaluationRepository mTechProposalEvaluationRepository, MTechProposalEvaluationMapper mTechProposalEvaluationMapper) {
        this.mTechProposalEvaluationRepository = mTechProposalEvaluationRepository;
        this.mTechProposalEvaluationMapper = mTechProposalEvaluationMapper;
    }

    /**
     * Save a mTechProposalEvaluation.
     *
     * @param mTechProposalEvaluationDTO the entity to save.
     * @return the persisted entity.
     */
    public MTechProposalEvaluationDTO save(MTechProposalEvaluationDTO mTechProposalEvaluationDTO) {
        log.debug("Request to save MTechProposalEvaluation : {}", mTechProposalEvaluationDTO);
        MTechProposalEvaluation mTechProposalEvaluation = mTechProposalEvaluationMapper.toEntity(mTechProposalEvaluationDTO);
        mTechProposalEvaluation = mTechProposalEvaluationRepository.save(mTechProposalEvaluation);
        return mTechProposalEvaluationMapper.toDto(mTechProposalEvaluation);
    }

    public List<MTechProposalEvaluationDTO> evaluation(List<MTechProposalEvaluationDTO> mTechProposalEvaluationDTOS){
        for(MTechProposalEvaluationDTO mTechProposalEvaluationDTO :mTechProposalEvaluationDTOS){
            save(mTechProposalEvaluationDTO);
        }
        return mTechProposalEvaluationDTOS;
    }

    /**
     * Get all the mTechProposalEvaluations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MTechProposalEvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MTechProposalEvaluations");
        return mTechProposalEvaluationRepository.findAll(pageable)
            .map(mTechProposalEvaluationMapper::toDto);
    }

    /**
     * Get one mTechProposalEvaluation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MTechProposalEvaluationDTO> findOne(Long id) {
        log.debug("Request to get MTechProposalEvaluation : {}", id);
        return mTechProposalEvaluationRepository.findById(id)
            .map(mTechProposalEvaluationMapper::toDto);
    }

    /**
     * Delete the mTechProposalEvaluation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MTechProposalEvaluation : {}", id);
        mTechProposalEvaluationRepository.deleteById(id);
    }
}
