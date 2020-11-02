package com.bhp.opusb.service;

import com.bhp.opusb.domain.CRegDocTypeBusinessCategory;
import com.bhp.opusb.repository.CRegDocTypeBusinessCategoryRepository;
import com.bhp.opusb.service.dto.CRegDocTypeBusinessCategoryDTO;
import com.bhp.opusb.service.mapper.CRegDocTypeBusinessCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CRegDocTypeBusinessCategory}.
 */
@Service
@Transactional
public class CRegDocTypeBusinessCategoryService {

    private final Logger log = LoggerFactory.getLogger(CRegDocTypeBusinessCategoryService.class);

    private final CRegDocTypeBusinessCategoryRepository cRegDocTypeBusinessCategoryRepository;

    private final CRegDocTypeBusinessCategoryMapper cRegDocTypeBusinessCategoryMapper;

    public CRegDocTypeBusinessCategoryService(CRegDocTypeBusinessCategoryRepository cRegDocTypeBusinessCategoryRepository, CRegDocTypeBusinessCategoryMapper cRegDocTypeBusinessCategoryMapper) {
        this.cRegDocTypeBusinessCategoryRepository = cRegDocTypeBusinessCategoryRepository;
        this.cRegDocTypeBusinessCategoryMapper = cRegDocTypeBusinessCategoryMapper;
    }

    /**
     * Save a cRegDocTypeBusinessCategory.
     *
     * @param cRegDocTypeBusinessCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public CRegDocTypeBusinessCategoryDTO save(CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO) {
        log.debug("Request to save CRegDocTypeBusinessCategory : {}", cRegDocTypeBusinessCategoryDTO);
        CRegDocTypeBusinessCategory cRegDocTypeBusinessCategory = cRegDocTypeBusinessCategoryMapper.toEntity(cRegDocTypeBusinessCategoryDTO);
        cRegDocTypeBusinessCategory = cRegDocTypeBusinessCategoryRepository.save(cRegDocTypeBusinessCategory);
        return cRegDocTypeBusinessCategoryMapper.toDto(cRegDocTypeBusinessCategory);
    }

    /**
     * Get all the cRegDocTypeBusinessCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CRegDocTypeBusinessCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CRegDocTypeBusinessCategories");
        return cRegDocTypeBusinessCategoryRepository.findAll(pageable)
            .map(cRegDocTypeBusinessCategoryMapper::toDto);
    }

    /**
     * Get one cRegDocTypeBusinessCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CRegDocTypeBusinessCategoryDTO> findOne(Long id) {
        log.debug("Request to get CRegDocTypeBusinessCategory : {}", id);
        return cRegDocTypeBusinessCategoryRepository.findById(id)
            .map(cRegDocTypeBusinessCategoryMapper::toDto);
    }

    /**
     * Delete the cRegDocTypeBusinessCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CRegDocTypeBusinessCategory : {}", id);
        cRegDocTypeBusinessCategoryRepository.deleteById(id);
    }
}
