package com.bhp.opusb.service;

import com.bhp.opusb.domain.MVendorSuggestion;
import com.bhp.opusb.repository.MVendorSuggestionRepository;
import com.bhp.opusb.service.dto.MVendorSuggestionDTO;
import com.bhp.opusb.service.mapper.MVendorSuggestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MVendorSuggestion}.
 */
@Service
@Transactional
public class MVendorSuggestionService {

    private final Logger log = LoggerFactory.getLogger(MVendorSuggestionService.class);

    private final MVendorSuggestionRepository mVendorSuggestionRepository;

    private final MVendorSuggestionMapper mVendorSuggestionMapper;

    public MVendorSuggestionService(MVendorSuggestionRepository mVendorSuggestionRepository, MVendorSuggestionMapper mVendorSuggestionMapper) {
        this.mVendorSuggestionRepository = mVendorSuggestionRepository;
        this.mVendorSuggestionMapper = mVendorSuggestionMapper;
    }

    /**
     * Save a mVendorSuggestion.
     *
     * @param mVendorSuggestionDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorSuggestionDTO save(MVendorSuggestionDTO mVendorSuggestionDTO) {
        log.debug("Request to save MVendorSuggestion : {}", mVendorSuggestionDTO);
        MVendorSuggestion mVendorSuggestion = mVendorSuggestionMapper.toEntity(mVendorSuggestionDTO);
        mVendorSuggestion = mVendorSuggestionRepository.save(mVendorSuggestion);
        return mVendorSuggestionMapper.toDto(mVendorSuggestion);
    }

    /**
     * Get all the mVendorSuggestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorSuggestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorSuggestions");
        return mVendorSuggestionRepository.findAll(pageable)
            .map(mVendorSuggestionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<MVendorSuggestion> findallnested() {
        return mVendorSuggestionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<MVendorSuggestion> findanested(Long id) {
        return mVendorSuggestionRepository.findById(id);
    }

    /**
     * Get one mVendorSuggestion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorSuggestionDTO> findOne(Long id) {
        log.debug("Request to get MVendorSuggestion : {}", id);
        return mVendorSuggestionRepository.findById(id)
            .map(mVendorSuggestionMapper::toDto);
    }

    /**
     * Delete the mVendorSuggestion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorSuggestion : {}", id);
        mVendorSuggestionRepository.deleteById(id);
    }
}
