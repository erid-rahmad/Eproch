package com.bhp.opusb.service;

import com.bhp.opusb.domain.MRfqLine;
import com.bhp.opusb.repository.MRfqLineRepository;
import com.bhp.opusb.service.dto.MRfqLineDTO;
import com.bhp.opusb.service.mapper.MRfqLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MRfqLine}.
 */
@Service
@Transactional
public class MRfqLineService {

    private final Logger log = LoggerFactory.getLogger(MRfqLineService.class);

    private final MRfqLineRepository mRfqLineRepository;

    private final MRfqLineMapper mRfqLineMapper;

    public MRfqLineService(MRfqLineRepository mRfqLineRepository, MRfqLineMapper mRfqLineMapper) {
        this.mRfqLineRepository = mRfqLineRepository;
        this.mRfqLineMapper = mRfqLineMapper;
    }

    /**
     * Save a mRfqLine.
     *
     * @param mRfqLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MRfqLineDTO save(MRfqLineDTO mRfqLineDTO) {
        log.debug("Request to save MRfqLine : {}", mRfqLineDTO);
        MRfqLine mRfqLine = mRfqLineMapper.toEntity(mRfqLineDTO);
        mRfqLine = mRfqLineRepository.save(mRfqLine);
        return mRfqLineMapper.toDto(mRfqLine);
    }

    /**
     * Get all the mRfqLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MRfqLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MRfqLines");
        return mRfqLineRepository.findAll(pageable)
            .map(mRfqLineMapper::toDto);
    }

    /**
     * Get one mRfqLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MRfqLineDTO> findOne(Long id) {
        log.debug("Request to get MRfqLine : {}", id);
        return mRfqLineRepository.findById(id)
            .map(mRfqLineMapper::toDto);
    }

    /**
     * Delete the mRfqLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MRfqLine : {}", id);
        mRfqLineRepository.deleteById(id);
    }
}
