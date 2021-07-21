package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPreqRegistEvaluation;
import com.bhp.opusb.repository.MPreqRegistEvaluationRepository;
import com.bhp.opusb.service.dto.MPreqRegistEvaluationDTO;
import com.bhp.opusb.service.mapper.MPreqRegistEvaluationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Implementation for managing {@link MPreqRegistEvaluation}.
 */
@Service
@Transactional
public class MPreqRegistEvaluationService {

    private final Logger log = LoggerFactory.getLogger(MPreqRegistEvaluationService.class);

    private final MPreqRegistEvaluationRepository mPreqRegistEvaluationRepository;

    private final MPreqRegistEvaluationMapper mPreqRegistEvaluationMapper;

    public MPreqRegistEvaluationService(MPreqRegistEvaluationRepository mPreqRegistEvaluationRepository, MPreqRegistEvaluationMapper mPreqRegistEvaluationMapper) {
        this.mPreqRegistEvaluationRepository = mPreqRegistEvaluationRepository;
        this.mPreqRegistEvaluationMapper = mPreqRegistEvaluationMapper;
    }

    /**
     * Save a mPreqRegistEvaluation.
     *
     * @param mPreqRegistEvaluationDTO the entity to save.
     * @return the persisted entity.
     */
    public MPreqRegistEvaluationDTO save(MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO) {
        log.debug("Request to save MPreqRegistEvaluation : {}", mPreqRegistEvaluationDTO);
        MPreqRegistEvaluation mPreqRegistEvaluation = mPreqRegistEvaluationMapper.toEntity(mPreqRegistEvaluationDTO);
        mPreqRegistEvaluation = mPreqRegistEvaluationRepository.save(mPreqRegistEvaluation);
        return mPreqRegistEvaluationMapper.toDto(mPreqRegistEvaluation);
    }

    /**
     * Get all the mPreqRegistEvaluations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPreqRegistEvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPreqRegistEvaluations");
        return mPreqRegistEvaluationRepository.findAll(pageable)
            .map(mPreqRegistEvaluationMapper::toDto);
    }

    /**
     * Get one mPreqRegistEvaluation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPreqRegistEvaluationDTO> findOne(Long id) {
        log.debug("Request to get MPreqRegistEvaluation : {}", id);
        return mPreqRegistEvaluationRepository.findById(id)
            .map(mPreqRegistEvaluationMapper::toDto);
    }

    /**
     * Delete the mPreqRegistEvaluation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPreqRegistEvaluation : {}", id);
        mPreqRegistEvaluationRepository.deleteById(id);
    }

    public List<MPreqRegistEvaluationDTO> processAll(@Valid List<MPreqRegistEvaluationDTO> dtos) {
        log.debug("Request to save MPreqRegistEvaluations : {}", dtos);
        for(MPreqRegistEvaluationDTO dto: dtos){
            Optional<MPreqRegistEvaluation> existingId = mPreqRegistEvaluationRepository.findExistingId(dto.getVendorId(), dto.getPrequalificationId());
            if(existingId.isPresent()) {
                MPreqRegistEvaluation eval = existingId.get();
                dto.setId(eval.getId());
                dto.setUid(eval.getUid());
                dto.setActive(eval.isActive());
            }
        }
        List<MPreqRegistEvaluation> entities = mPreqRegistEvaluationMapper.toEntity(dtos);
        entities = mPreqRegistEvaluationRepository.saveAll(entities);
        return mPreqRegistEvaluationMapper.toDto(entities);
    }
}
