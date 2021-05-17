package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;

import com.bhp.opusb.domain.MVendorSuggestion;
import com.bhp.opusb.repository.MVendorSuggestionRepository;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MVendorSuggestionDTO;
import com.bhp.opusb.service.mapper.MVendorSuggestionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Save a list of mVendorSuggestions.
     *
     * @param mVendorSuggestionDTOs the entities to save.
     * @return the persisted entities.
     */
    public List<MVendorSuggestionDTO> saveAll(List<MVendorSuggestionDTO> mVendorSuggestionDTOs, MBiddingDTO mBiddingDTO) {
        log.debug("Request to save MVendorSuggestions : {}", mVendorSuggestionDTOs);

        mVendorSuggestionDTOs.forEach(suggestion -> {
            suggestion.setBiddingId(mBiddingDTO.getId());
            suggestion.setAdOrganizationId(mBiddingDTO.getAdOrganizationId());
        });

        List<MVendorSuggestion> mVendorSuggestions = mVendorSuggestionMapper.toEntity(mVendorSuggestionDTOs);
        return mVendorSuggestionMapper.toDto(mVendorSuggestionRepository.saveAll(mVendorSuggestions));
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

    /**
     * Delete the given mVendorSuggestions.
     *
     * @param mVendorSuggestionDTOs the given mVendorSuggestions.
     */
    public void deleteAll(List<MVendorSuggestionDTO> mVendorSuggestionDTOs) {
        log.debug("Request to delete all MVendorSuggestions. count : {}", mVendorSuggestionDTOs.size());
        mVendorSuggestionRepository.deleteAll(mVendorSuggestionMapper.toEntity(mVendorSuggestionDTOs));
    }
}
