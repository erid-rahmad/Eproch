package com.bhp.opusb.service;

import com.bhp.opusb.domain.CProductCategory;
import com.bhp.opusb.repository.CProductCategoryRepository;
import com.bhp.opusb.service.dto.CProductCategoryDTO;
import com.bhp.opusb.service.mapper.CProductCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CProductCategory}.
 */
@Service
@Transactional
public class CProductCategoryService {

    private final Logger log = LoggerFactory.getLogger(CProductCategoryService.class);

    private final CProductCategoryRepository cProductCategoryRepository;

    private final CProductCategoryMapper cProductCategoryMapper;

    public CProductCategoryService(CProductCategoryRepository cProductCategoryRepository, CProductCategoryMapper cProductCategoryMapper) {
        this.cProductCategoryRepository = cProductCategoryRepository;
        this.cProductCategoryMapper = cProductCategoryMapper;
    }

    /**
     * Save a cProductCategory.
     *
     * @param cProductCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public CProductCategoryDTO save(CProductCategoryDTO cProductCategoryDTO) {
        log.debug("Request to save CProductCategory : {}", cProductCategoryDTO);
        CProductCategory cProductCategory = cProductCategoryMapper.toEntity(cProductCategoryDTO);
        cProductCategory = cProductCategoryRepository.save(cProductCategory);
        return cProductCategoryMapper.toDto(cProductCategory);
    }

    /**
     * Get all the cProductCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CProductCategories");
        return cProductCategoryRepository.findAll(pageable)
            .map(cProductCategoryMapper::toDto);
    }

    /**
     * Get one cProductCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CProductCategoryDTO> findOne(Long id) {
        log.debug("Request to get CProductCategory : {}", id);
        return cProductCategoryRepository.findById(id)
            .map(cProductCategoryMapper::toDto);
    }

    /**
     * Delete the cProductCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CProductCategory : {}", id);
        cProductCategoryRepository.deleteById(id);
    }
}
