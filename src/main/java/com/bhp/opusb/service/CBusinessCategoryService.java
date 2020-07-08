package com.bhp.opusb.service;

import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.repository.CBusinessCategoryRepository;
import com.bhp.opusb.service.dto.CBusinessCategoryDTO;
import com.bhp.opusb.service.mapper.CBusinessCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CBusinessCategory}.
 */
@Service
@Transactional
public class CBusinessCategoryService {

    private final Logger log = LoggerFactory.getLogger(CBusinessCategoryService.class);

    private final CBusinessCategoryRepository cBusinessCategoryRepository;

    private final CBusinessCategoryMapper cBusinessCategoryMapper;

    public CBusinessCategoryService(CBusinessCategoryRepository cBusinessCategoryRepository, CBusinessCategoryMapper cBusinessCategoryMapper) {
        this.cBusinessCategoryRepository = cBusinessCategoryRepository;
        this.cBusinessCategoryMapper = cBusinessCategoryMapper;
    }

    /**
     * Save a cBusinessCategory.
     *
     * @param cBusinessCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public CBusinessCategoryDTO save(CBusinessCategoryDTO cBusinessCategoryDTO) {
        log.debug("Request to save CBusinessCategory : {}", cBusinessCategoryDTO);
        CBusinessCategory cBusinessCategory = cBusinessCategoryMapper.toEntity(cBusinessCategoryDTO);
        cBusinessCategory = cBusinessCategoryRepository.save(cBusinessCategory);
        return cBusinessCategoryMapper.toDto(cBusinessCategory);
    }

    /**
     * Get all the cBusinessCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CBusinessCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CBusinessCategories");
        return cBusinessCategoryRepository.findAll(pageable)
            .map(cBusinessCategoryMapper::toDto);
    }

    /**
     * Get one cBusinessCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CBusinessCategoryDTO> findOne(Long id) {
        log.debug("Request to get CBusinessCategory : {}", id);
        return cBusinessCategoryRepository.findById(id)
            .map(cBusinessCategoryMapper::toDto);
    }

    /**
     * Delete the cBusinessCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CBusinessCategory : {}", id);
        cBusinessCategoryRepository.deleteById(id);
    }
}
