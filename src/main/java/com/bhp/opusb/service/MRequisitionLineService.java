package com.bhp.opusb.service;

import com.bhp.opusb.domain.MRequisitionLine;
import com.bhp.opusb.repository.MRequisitionLineRepository;
import com.bhp.opusb.service.dto.MRequisitionLineDTO;
import com.bhp.opusb.service.mapper.MRequisitionLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MRequisitionLine}.
 */
@Service
@Transactional
public class MRequisitionLineService {

    private final Logger log = LoggerFactory.getLogger(MRequisitionLineService.class);

    private final MRequisitionLineRepository mRequisitionLineRepository;

    private final MRequisitionLineMapper mRequisitionLineMapper;

    public MRequisitionLineService(MRequisitionLineRepository mRequisitionLineRepository, MRequisitionLineMapper mRequisitionLineMapper) {
        this.mRequisitionLineRepository = mRequisitionLineRepository;
        this.mRequisitionLineMapper = mRequisitionLineMapper;
    }

    /**
     * Save a mRequisitionLine.
     *
     * @param mRequisitionLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MRequisitionLineDTO save(MRequisitionLineDTO mRequisitionLineDTO) {
        log.debug("Request to save MRequisitionLine : {}", mRequisitionLineDTO);
        MRequisitionLine mRequisitionLine = mRequisitionLineMapper.toEntity(mRequisitionLineDTO);
        mRequisitionLine = mRequisitionLineRepository.save(mRequisitionLine);
        return mRequisitionLineMapper.toDto(mRequisitionLine);
    }

    /**
     * Get all the mRequisitionLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MRequisitionLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MRequisitionLines");
        return mRequisitionLineRepository.findAll(pageable)
            .map(mRequisitionLineMapper::toDto);
    }

    /**
     * Get one mRequisitionLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MRequisitionLineDTO> findOne(Long id) {
        log.debug("Request to get MRequisitionLine : {}", id);
        return mRequisitionLineRepository.findById(id)
            .map(mRequisitionLineMapper::toDto);
    }

    /**
     * Delete the mRequisitionLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MRequisitionLine : {}", id);
        mRequisitionLineRepository.deleteById(id);
    }
}
