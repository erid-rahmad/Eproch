package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalVendorSuggestion;
import com.bhp.opusb.repository.MPrequalVendorSuggestionRepository;
import com.bhp.opusb.service.dto.MPrequalVendorSuggestionDTO;
import com.bhp.opusb.service.dto.MPrequalificationInformationDTO;
import com.bhp.opusb.service.mapper.MPrequalVendorSuggestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MPrequalVendorSuggestion}.
 */
@Service
@Transactional
public class MPrequalVendorSuggestionService {

    private final Logger log = LoggerFactory.getLogger(MPrequalVendorSuggestionService.class);

    private final MPrequalVendorSuggestionRepository mPrequalVendorSuggestionRepository;

    private final MPrequalVendorSuggestionMapper mPrequalVendorSuggestionMapper;

    public MPrequalVendorSuggestionService(MPrequalVendorSuggestionRepository mPrequalVendorSuggestionRepository, MPrequalVendorSuggestionMapper mPrequalVendorSuggestionMapper) {
        this.mPrequalVendorSuggestionRepository = mPrequalVendorSuggestionRepository;
        this.mPrequalVendorSuggestionMapper = mPrequalVendorSuggestionMapper;
    }

    /**
     * Save a mPrequalVendorSuggestion.
     *
     * @param mPrequalVendorSuggestionDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalVendorSuggestionDTO save(MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO) {
        log.debug("Request to save MPrequalVendorSuggestion : {}", mPrequalVendorSuggestionDTO);
        MPrequalVendorSuggestion mPrequalVendorSuggestion = mPrequalVendorSuggestionMapper.toEntity(mPrequalVendorSuggestionDTO);
        mPrequalVendorSuggestion = mPrequalVendorSuggestionRepository.save(mPrequalVendorSuggestion);
        return mPrequalVendorSuggestionMapper.toDto(mPrequalVendorSuggestion);
    }

    /**
     * Save a list of mVendorSuggestions.
     *
     * @param mVendorSuggestionDTOs the entities to save.
     * @return the persisted entities.
     */
    public List<MPrequalVendorSuggestionDTO> saveAll(List<MPrequalVendorSuggestionDTO> mVendorSuggestionDTOs, 
        MPrequalificationInformationDTO mPreqInfoDTO) {
        log.debug("Request to save MPrequalVendorSuggestions : {}", mVendorSuggestionDTOs);

        mVendorSuggestionDTOs.forEach(suggestion -> {
            suggestion.setPrequalificationId(mPreqInfoDTO.getId());
            suggestion.setAdOrganizationId(mPreqInfoDTO.getAdOrganizationId());
        });

        List<MPrequalVendorSuggestion> mVendorSuggestions = mPrequalVendorSuggestionMapper.toEntity(mVendorSuggestionDTOs);
        return mPrequalVendorSuggestionMapper.toDto(mPrequalVendorSuggestionRepository.saveAll(mVendorSuggestions));
    }

    /**
     * Get all the mPrequalVendorSuggestions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalVendorSuggestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalVendorSuggestions");
        return mPrequalVendorSuggestionRepository.findAll(pageable)
            .map(mPrequalVendorSuggestionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<MPrequalVendorSuggestion> findallnested() {
        return mPrequalVendorSuggestionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<MPrequalVendorSuggestion> findanested(Long id) {
        return mPrequalVendorSuggestionRepository.findById(id);
    }

    /**
     * Get one mPrequalVendorSuggestion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalVendorSuggestionDTO> findOne(Long id) {
        log.debug("Request to get MPrequalVendorSuggestion : {}", id);
        return mPrequalVendorSuggestionRepository.findById(id)
            .map(mPrequalVendorSuggestionMapper::toDto);
    }

    /**
     * Delete the mPrequalVendorSuggestion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalVendorSuggestion : {}", id);
        mPrequalVendorSuggestionRepository.deleteById(id);
    }

    /**
     * Delete the given mVendorSuggestions.
     *
     * @param mVendorSuggestionDTOs the given mVendorSuggestions.
     */
    public void deleteAll(List<MPrequalVendorSuggestionDTO> mVendorSuggestionDTOs) {
        log.debug("Request to delete all MVendorSuggestions. count : {}", mVendorSuggestionDTOs.size());
        mPrequalVendorSuggestionRepository.deleteAll(mPrequalVendorSuggestionMapper.toEntity(mVendorSuggestionDTOs));
    }
}
