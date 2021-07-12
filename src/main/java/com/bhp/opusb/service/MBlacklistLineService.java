package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBlacklistLine;
import com.bhp.opusb.repository.MBlacklistLineRepository;
import com.bhp.opusb.service.dto.MBlacklistLineDTO;
import com.bhp.opusb.service.mapper.MBlacklistLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBlacklistLine}.
 */
@Service
@Transactional
public class MBlacklistLineService {

    private final Logger log = LoggerFactory.getLogger(MBlacklistLineService.class);

    private final MBlacklistLineRepository mBlacklistLineRepository;

    private final MBlacklistLineMapper mBlacklistLineMapper;

    public MBlacklistLineService(MBlacklistLineRepository mBlacklistLineRepository, MBlacklistLineMapper mBlacklistLineMapper) {
        this.mBlacklistLineRepository = mBlacklistLineRepository;
        this.mBlacklistLineMapper = mBlacklistLineMapper;
    }

    /**
     * Save a mBlacklistLine.
     *
     * @param mBlacklistLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MBlacklistLineDTO save(MBlacklistLineDTO mBlacklistLineDTO) {
        log.debug("Request to save MBlacklistLine : {}", mBlacklistLineDTO);
        MBlacklistLine mBlacklistLine = mBlacklistLineMapper.toEntity(mBlacklistLineDTO);
        mBlacklistLine = mBlacklistLineRepository.save(mBlacklistLine);
        return mBlacklistLineMapper.toDto(mBlacklistLine);
    }

    /**
     * Get all the mBlacklistLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBlacklistLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBlacklistLines");
        return mBlacklistLineRepository.findAll(pageable)
            .map(mBlacklistLineMapper::toDto);
    }

    /**
     * Get one mBlacklistLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBlacklistLineDTO> findOne(Long id) {
        log.debug("Request to get MBlacklistLine : {}", id);
        return mBlacklistLineRepository.findById(id)
            .map(mBlacklistLineMapper::toDto);
    }

    /**
     * Delete the mBlacklistLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBlacklistLine : {}", id);
        mBlacklistLineRepository.deleteById(id);
    }
}
