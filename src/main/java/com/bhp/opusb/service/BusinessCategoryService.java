package com.bhp.opusb.service;

import com.bhp.opusb.domain.BusinessCategory;
import com.bhp.opusb.repository.BusinessCategoryRepository;
import com.bhp.opusb.service.dto.BusinessCategoryDTO;
import com.bhp.opusb.service.mapper.BusinessCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BusinessCategory}.
 */
@Service
@Transactional
public class BusinessCategoryService {

    private final Logger log = LoggerFactory.getLogger(BusinessCategoryService.class);

    private final BusinessCategoryRepository businessCategoryRepository;

    private final BusinessCategoryMapper businessCategoryMapper;

    public BusinessCategoryService(BusinessCategoryRepository businessCategoryRepository, BusinessCategoryMapper businessCategoryMapper) {
        this.businessCategoryRepository = businessCategoryRepository;
        this.businessCategoryMapper = businessCategoryMapper;
    }

    /**
     * Save a businessCategory.
     *
     * @param businessCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public BusinessCategoryDTO save(BusinessCategoryDTO businessCategoryDTO) {
        log.debug("Request to save BusinessCategory : {}", businessCategoryDTO);
        BusinessCategory businessCategory = businessCategoryMapper.toEntity(businessCategoryDTO);
        businessCategory = businessCategoryRepository.save(businessCategory);
        return businessCategoryMapper.toDto(businessCategory);
    }

    /**
     * Get all the businessCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessCategories");
        return businessCategoryRepository.findAll(pageable)
            .map(businessCategoryMapper::toDto);
    }

    /**
     * Get one businessCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BusinessCategoryDTO> findOne(Long id) {
        log.debug("Request to get BusinessCategory : {}", id);
        return businessCategoryRepository.findById(id)
            .map(businessCategoryMapper::toDto);
    }

    /**
     * Delete the businessCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BusinessCategory : {}", id);
        businessCategoryRepository.deleteById(id);
    }
}
