package com.bhp.opusb.service;

import com.bhp.opusb.domain.MVendorConfirmationLine;
import com.bhp.opusb.domain.MVendorConfirmationResponse;
import com.bhp.opusb.repository.MVendorConfirmationLineRepository;
import com.bhp.opusb.repository.MVendorConfirmationResponseRepository;
import com.bhp.opusb.service.dto.MVendorConfirmationResponseDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MVendorConfirmationResponse}.
 */
@Service
@Transactional
public class MVendorConfirmationResponseService {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationResponseService.class);

    private final MVendorConfirmationResponseRepository mVendorConfirmationResponseRepository;

    private final MVendorConfirmationResponseMapper mVendorConfirmationResponseMapper;
    
    private final MVendorConfirmationLineRepository mVendorConfirmationLineRepository;

    public MVendorConfirmationResponseService(
        MVendorConfirmationResponseRepository mVendorConfirmationResponseRepository, 
        MVendorConfirmationResponseMapper mVendorConfirmationResponseMapper,
        MVendorConfirmationLineRepository mVendorConfirmationLineRepository
    ) {
        this.mVendorConfirmationResponseRepository = mVendorConfirmationResponseRepository;
        this.mVendorConfirmationResponseMapper = mVendorConfirmationResponseMapper;
        this.mVendorConfirmationLineRepository = mVendorConfirmationLineRepository;
    }

    /**
     * Save a mVendorConfirmationResponse.
     *
     * @param mVendorConfirmationResponseDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorConfirmationResponseDTO save(MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO) {
        log.debug("Request to save MVendorConfirmationResponse : {}", mVendorConfirmationResponseDTO);
        MVendorConfirmationResponse mVendorConfirmationResponse = mVendorConfirmationResponseMapper.toEntity(mVendorConfirmationResponseDTO);
        mVendorConfirmationResponse.setActive(true);

        MVendorConfirmationLine mvcl = mVendorConfirmationLineRepository.findById(mVendorConfirmationResponseDTO.getVendorConfirmationLineId()).get();
        mvcl.setStatus(mVendorConfirmationResponse.getAccept()==null?"R":"A");

        mVendorConfirmationLineRepository.save(mvcl);
        mVendorConfirmationResponse = mVendorConfirmationResponseRepository.save(mVendorConfirmationResponse);
        return mVendorConfirmationResponseMapper.toDto(mVendorConfirmationResponse);
    }

    /**
     * Get all the mVendorConfirmationResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorConfirmationResponseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorConfirmationResponses");
        return mVendorConfirmationResponseRepository.findAll(pageable)
            .map(mVendorConfirmationResponseMapper::toDto);
    }

    /**
     * Get one mVendorConfirmationResponse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorConfirmationResponseDTO> findOne(Long id) {
        log.debug("Request to get MVendorConfirmationResponse : {}", id);
        return mVendorConfirmationResponseRepository.findById(id)
            .map(mVendorConfirmationResponseMapper::toDto);
    }

    /**
     * Delete the mVendorConfirmationResponse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorConfirmationResponse : {}", id);
        mVendorConfirmationResponseRepository.deleteById(id);
    }
}
