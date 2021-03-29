package com.bhp.opusb.service;

import com.bhp.opusb.domain.CQuestionCategory;
import com.bhp.opusb.repository.CQuestionCategoryRepository;
import com.bhp.opusb.service.dto.CQuestionCategoryDTO;
import com.bhp.opusb.service.mapper.CQuestionCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CQuestionCategory}.
 */
@Service
@Transactional
public class CQuestionCategoryService {

    private final Logger log = LoggerFactory.getLogger(CQuestionCategoryService.class);

    private final CQuestionCategoryRepository cQuestionCategoryRepository;

    private final CQuestionCategoryMapper cQuestionCategoryMapper;

    public CQuestionCategoryService(CQuestionCategoryRepository cQuestionCategoryRepository, CQuestionCategoryMapper cQuestionCategoryMapper) {
        this.cQuestionCategoryRepository = cQuestionCategoryRepository;
        this.cQuestionCategoryMapper = cQuestionCategoryMapper;
    }

    /**
     * Save a cQuestionCategory.
     *
     * @param cQuestionCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    public CQuestionCategoryDTO save(CQuestionCategoryDTO cQuestionCategoryDTO) {
        log.debug("Request to save CQuestionCategory : {}", cQuestionCategoryDTO);
        CQuestionCategory cQuestionCategory = cQuestionCategoryMapper.toEntity(cQuestionCategoryDTO);
        cQuestionCategory = cQuestionCategoryRepository.save(cQuestionCategory);
        return cQuestionCategoryMapper.toDto(cQuestionCategory);
    }

    /**
     * Get all the cQuestionCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CQuestionCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CQuestionCategories");
        return cQuestionCategoryRepository.findAll(pageable)
            .map(cQuestionCategoryMapper::toDto);
    }

    /**
     * Get one cQuestionCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CQuestionCategoryDTO> findOne(Long id) {
        log.debug("Request to get CQuestionCategory : {}", id);
        return cQuestionCategoryRepository.findById(id)
            .map(cQuestionCategoryMapper::toDto);
    }

    /**
     * Delete the cQuestionCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CQuestionCategory : {}", id);
        cQuestionCategoryRepository.deleteById(id);
    }
}
