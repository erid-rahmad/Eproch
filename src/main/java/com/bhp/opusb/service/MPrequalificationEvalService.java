package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalificationEval;
import com.bhp.opusb.repository.MPrequalificationEvalRepository;
import com.bhp.opusb.service.dto.MPrequalificationEvalDTO;
import com.bhp.opusb.service.mapper.MPrequalificationEvalMapper;
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
 * Service Implementation for managing {@link MPrequalificationEval}.
 */
@Service
@Transactional
public class MPrequalificationEvalService {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationEvalService.class);

    private final MPrequalificationEvalRepository mPrequalificationEvalRepository;

    private final MPrequalificationEvalMapper mPrequalificationEvalMapper;

    public MPrequalificationEvalService(MPrequalificationEvalRepository mPrequalificationEvalRepository,
            MPrequalificationEvalMapper mPrequalificationEvalMapper) {
        this.mPrequalificationEvalRepository = mPrequalificationEvalRepository;
        this.mPrequalificationEvalMapper = mPrequalificationEvalMapper;
    }

    /**
     * Save a mPrequalificationEval.
     *
     * @param mPrequalificationEvalDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalificationEvalDTO save(MPrequalificationEvalDTO mPrequalificationEvalDTO) {
        log.debug("Request to save MPrequalificationEval : {}", mPrequalificationEvalDTO);
        MPrequalificationEval mPrequalificationEval = mPrequalificationEvalMapper.toEntity(mPrequalificationEvalDTO);
        mPrequalificationEval = mPrequalificationEvalRepository.save(mPrequalificationEval);
        return mPrequalificationEvalMapper.toDto(mPrequalificationEval);
    }

    /**
     * Get all the mPrequalificationEvals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationEvalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalificationEvals");
        return mPrequalificationEvalRepository.findAll(pageable).map(mPrequalificationEvalMapper::toDto);
    }

    /**
     * Get one mPrequalificationEval by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalificationEvalDTO> findOne(Long id) {
        log.debug("Request to get MPrequalificationEval : {}", id);
        return mPrequalificationEvalRepository.findById(id).map(mPrequalificationEvalMapper::toDto);
    }

    /**
     * Delete the mPrequalificationEval by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalificationEval : {}", id);
        mPrequalificationEvalRepository.deleteById(id);
    }

    public List<MPrequalificationEvalDTO> saveRequirements(
            @Valid List<MPrequalificationEvalDTO> mPrequalificationEvalDTOs) {
        log.debug("Request to save MProposalAdministrations. size : {}", mPrequalificationEvalDTOs.size());
        List<MPrequalificationEval> mPrequalificationEvals = mPrequalificationEvalMapper
                .toEntity(mPrequalificationEvalDTOs);
                mPrequalificationEvals = mPrequalificationEvalRepository.saveAll(mPrequalificationEvals);
        return mPrequalificationEvalMapper.toDto(mPrequalificationEvals);
    }
}
