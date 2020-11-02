package com.bhp.opusb.service;

import com.bhp.opusb.domain.CTaxCategory;
import com.bhp.opusb.repository.CTaxCategoryRepository;
import com.bhp.opusb.service.dto.CTaxCategoryDTO;
import com.bhp.opusb.service.mapper.CTaxCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CTaxCategory}.
 */
@Service
@Transactional
public class CTaxCategoryService {

    private final Logger log = LoggerFactory.getLogger(CTaxCategoryService.class);

    private final CTaxCategoryRepository cTaxCategoryRepository;

    private final CTaxCategoryMapper cTaxCategoryMapper;

    public CTaxCategoryService(CTaxCategoryRepository cTaxCategoryRepository, CTaxCategoryMapper cTaxCategoryMapper) {
        this.cTaxCategoryRepository = cTaxCategoryRepository;
        this.cTaxCategoryMapper = cTaxCategoryMapper;
    }

    /**
     * Save a cTaxCategory.
     *
     * @param cTaxCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public CTaxCategoryDTO save(CTaxCategoryDTO cTaxCategoryDTO) {
        log.debug("Request to save CTaxCategory : {}", cTaxCategoryDTO);
        CTaxCategory cTaxCategory = cTaxCategoryMapper.toEntity(cTaxCategoryDTO);
        cTaxCategory = cTaxCategoryRepository.save(cTaxCategory);
        return cTaxCategoryMapper.toDto(cTaxCategory);
    }

    /**
     * Get all the cTaxCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CTaxCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CTaxCategories");
        return cTaxCategoryRepository.findAll(pageable)
            .map(cTaxCategoryMapper::toDto);
    }

    /**
     * Get one cTaxCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CTaxCategoryDTO> findOne(Long id) {
        log.debug("Request to get CTaxCategory : {}", id);
        return cTaxCategoryRepository.findById(id)
            .map(cTaxCategoryMapper::toDto);
    }

    /**
     * Delete the cTaxCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CTaxCategory : {}", id);
        cTaxCategoryRepository.deleteById(id);
    }
}
