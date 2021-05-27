package com.bhp.opusb.service;

import com.bhp.opusb.domain.MVendorConfirmationLine;
import com.bhp.opusb.repository.MVendorConfirmationLineRepository;
import com.bhp.opusb.service.dto.MVendorConfirmationLineDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MVendorConfirmationLine}.
 */
@Service
@Transactional
public class MVendorConfirmationLineService {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationLineService.class);

    private final MVendorConfirmationLineRepository mVendorConfirmationLineRepository;

    private final MVendorConfirmationLineMapper mVendorConfirmationLineMapper;

    public MVendorConfirmationLineService(MVendorConfirmationLineRepository mVendorConfirmationLineRepository, MVendorConfirmationLineMapper mVendorConfirmationLineMapper) {
        this.mVendorConfirmationLineRepository = mVendorConfirmationLineRepository;
        this.mVendorConfirmationLineMapper = mVendorConfirmationLineMapper;
    }

    /**
     * Save a mVendorConfirmationLine.
     *
     * @param mVendorConfirmationLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorConfirmationLineDTO save(MVendorConfirmationLineDTO mVendorConfirmationLineDTO) {
        log.debug("Request to save MVendorConfirmationLine : {}", mVendorConfirmationLineDTO);
        MVendorConfirmationLine mVendorConfirmationLine = mVendorConfirmationLineMapper.toEntity(mVendorConfirmationLineDTO);
        mVendorConfirmationLine = mVendorConfirmationLineRepository.save(mVendorConfirmationLine);
        return mVendorConfirmationLineMapper.toDto(mVendorConfirmationLine);
    }

    /**
     * Get all the mVendorConfirmationLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorConfirmationLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorConfirmationLines");
        return mVendorConfirmationLineRepository.findAll(pageable)
            .map(mVendorConfirmationLineMapper::toDto);
    }

    /**
     * Get one mVendorConfirmationLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorConfirmationLineDTO> findOne(Long id) {
        log.debug("Request to get MVendorConfirmationLine : {}", id);
        return mVendorConfirmationLineRepository.findById(id)
            .map(mVendorConfirmationLineMapper::toDto);
    }

    /**
     * Delete the mVendorConfirmationLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorConfirmationLine : {}", id);
        mVendorConfirmationLineRepository.deleteById(id);
    }
}
