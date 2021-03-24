package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingSubItemLine;
import com.bhp.opusb.repository.MBiddingSubItemLineRepository;
import com.bhp.opusb.service.dto.MBiddingSubItemLineDTO;
import com.bhp.opusb.service.mapper.MBiddingSubItemLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.loader.tools.LibraryScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingSubItemLine}.
 */
@Service
@Transactional
public class MBiddingSubItemLineService {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubItemLineService.class);

    private final MBiddingSubItemLineRepository mBiddingSubItemLineRepository;

    private final MBiddingSubItemLineMapper mBiddingSubItemLineMapper;

    public MBiddingSubItemLineService(MBiddingSubItemLineRepository mBiddingSubItemLineRepository, MBiddingSubItemLineMapper mBiddingSubItemLineMapper) {
        this.mBiddingSubItemLineRepository = mBiddingSubItemLineRepository;
        this.mBiddingSubItemLineMapper = mBiddingSubItemLineMapper;
    }

    /**
     * Save a mBiddingSubItemLine.
     *
     * @param mBiddingSubItemLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingSubItemLineDTO save(MBiddingSubItemLineDTO mBiddingSubItemLineDTO) {
        log.debug("Request to save MBiddingSubItemLine : {}", mBiddingSubItemLineDTO);
        MBiddingSubItemLine mBiddingSubItemLine = mBiddingSubItemLineMapper.toEntity(mBiddingSubItemLineDTO);
        mBiddingSubItemLine = mBiddingSubItemLineRepository.save(mBiddingSubItemLine);
        return mBiddingSubItemLineMapper.toDto(mBiddingSubItemLine);
    }

    /**
     * Save all mBiddingSubItemLines.
     *
     * @param mBiddingSubItemLineDTOs the list of entities to save.
     * @return the persisted entities.
     */
    public List<MBiddingSubItemLineDTO> saveAll(List<MBiddingSubItemLineDTO> mBiddingSubItemLineDTOs) {
        log.debug("Request to save MBiddingSubItemLine : {}", mBiddingSubItemLineDTOs);
        List<MBiddingSubItemLine> mBiddingSubItemLines1 = mBiddingSubItemLineMapper.toEntity(mBiddingSubItemLineDTOs);
        mBiddingSubItemLines1 = mBiddingSubItemLineRepository.saveAll(mBiddingSubItemLines1);
        return mBiddingSubItemLineMapper.toDto(mBiddingSubItemLines1);
    }

    /**
     * Get all the mBiddingSubItemLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingSubItemLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingSubItemLines");
        return mBiddingSubItemLineRepository.findAll(pageable)
            .map(mBiddingSubItemLineMapper::toDto);
    }

    /**
     * Get one mBiddingSubItemLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingSubItemLineDTO> findOne(Long id) {
        log.debug("Request to get MBiddingSubItemLine : {}", id);
        return mBiddingSubItemLineRepository.findById(id)
            .map(mBiddingSubItemLineMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<MBiddingSubItemLine> findOnebyheader(Long id) {
        log.debug("Request to get MBiddingSubItemLine : {}", id);
        return mBiddingSubItemLineRepository.findbyheaderid(id);
    }

    /**
     * Delete the mBiddingSubItemLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingSubItemLine : {}", id);
        mBiddingSubItemLineRepository.deleteById(id);
    }
}
