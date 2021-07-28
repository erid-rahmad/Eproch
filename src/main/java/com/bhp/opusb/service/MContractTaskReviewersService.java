package com.bhp.opusb.service;

import com.bhp.opusb.domain.MContractTaskReviewers;
import com.bhp.opusb.repository.MContractTaskReviewersRepository;
import com.bhp.opusb.service.dto.MContractTaskReviewersDTO;
import com.bhp.opusb.service.mapper.MContractTaskReviewersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MContractTaskReviewers}.
 */
@Service
@Transactional
public class MContractTaskReviewersService {

    private final Logger log = LoggerFactory.getLogger(MContractTaskReviewersService.class);

    private final MContractTaskReviewersRepository mContractTaskReviewersRepository;

    private final MContractTaskReviewersMapper mContractTaskReviewersMapper;

    public MContractTaskReviewersService(MContractTaskReviewersRepository mContractTaskReviewersRepository, MContractTaskReviewersMapper mContractTaskReviewersMapper) {
        this.mContractTaskReviewersRepository = mContractTaskReviewersRepository;
        this.mContractTaskReviewersMapper = mContractTaskReviewersMapper;
    }

    /**
     * Save a mContractTaskReviewers.
     *
     * @param mContractTaskReviewersDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractTaskReviewersDTO save(MContractTaskReviewersDTO mContractTaskReviewersDTO) {
        log.debug("Request to save MContractTaskReviewers : {}", mContractTaskReviewersDTO);
        MContractTaskReviewers mContractTaskReviewers = mContractTaskReviewersMapper.toEntity(mContractTaskReviewersDTO);
        mContractTaskReviewers = mContractTaskReviewersRepository.save(mContractTaskReviewers);
        return mContractTaskReviewersMapper.toDto(mContractTaskReviewers);
    }

    /**
     * Get all the mContractTaskReviewers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractTaskReviewersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContractTaskReviewers");
        return mContractTaskReviewersRepository.findAll(pageable)
            .map(mContractTaskReviewersMapper::toDto);
    }

    /**
     * Get one mContractTaskReviewers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractTaskReviewersDTO> findOne(Long id) {
        log.debug("Request to get MContractTaskReviewers : {}", id);
        return mContractTaskReviewersRepository.findById(id)
            .map(mContractTaskReviewersMapper::toDto);
    }

    /**
     * Delete the mContractTaskReviewers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContractTaskReviewers : {}", id);
        mContractTaskReviewersRepository.deleteById(id);
    }
}
