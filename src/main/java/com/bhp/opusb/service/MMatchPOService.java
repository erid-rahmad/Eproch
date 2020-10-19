package com.bhp.opusb.service;

import com.bhp.opusb.domain.MMatchPO;
import com.bhp.opusb.repository.MMatchPORepository;
import com.bhp.opusb.service.dto.MMatchPODTO;
import com.bhp.opusb.service.mapper.MMatchPOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MMatchPO}.
 */
@Service
@Transactional
public class MMatchPOService {

    private final Logger log = LoggerFactory.getLogger(MMatchPOService.class);

    private final MMatchPORepository mMatchPORepository;

    private final MMatchPOMapper mMatchPOMapper;

    public MMatchPOService(MMatchPORepository mMatchPORepository, MMatchPOMapper mMatchPOMapper) {
        this.mMatchPORepository = mMatchPORepository;
        this.mMatchPOMapper = mMatchPOMapper;
    }

    /**
     * Save a mMatchPO.
     *
     * @param mMatchPODTO the entity to save.
     * @return the persisted entity.
     */
    public MMatchPODTO save(MMatchPODTO mMatchPODTO) {
        log.debug("Request to save MMatchPO : {}", mMatchPODTO);
        MMatchPO mMatchPO = mMatchPOMapper.toEntity(mMatchPODTO);
        mMatchPO = mMatchPORepository.save(mMatchPO);
        return mMatchPOMapper.toDto(mMatchPO);
    }

    /**
     * Get all the mMatchPOS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MMatchPODTO> findAll(Pageable pageable) {
        log.debug("Request to get all MMatchPOS");
        return mMatchPORepository.findAll(pageable)
            .map(mMatchPOMapper::toDto);
    }

    /**
     * Get one mMatchPO by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MMatchPODTO> findOne(Long id) {
        log.debug("Request to get MMatchPO : {}", id);
        return mMatchPORepository.findById(id)
            .map(mMatchPOMapper::toDto);
    }

    /**
     * Delete the mMatchPO by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MMatchPO : {}", id);
        mMatchPORepository.deleteById(id);
    }
}
