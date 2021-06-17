package com.bhp.opusb.service;

import com.bhp.opusb.domain.MRfq;
import com.bhp.opusb.repository.MRfqRepository;
import com.bhp.opusb.service.dto.MRfqDTO;
import com.bhp.opusb.service.mapper.MRfqMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MRfq}.
 */
@Service
@Transactional
public class MRfqService {

    private final Logger log = LoggerFactory.getLogger(MRfqService.class);

    private final MRfqRepository mRfqRepository;

    private final MRfqMapper mRfqMapper;

    public MRfqService(MRfqRepository mRfqRepository, MRfqMapper mRfqMapper) {
        this.mRfqRepository = mRfqRepository;
        this.mRfqMapper = mRfqMapper;
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
}
