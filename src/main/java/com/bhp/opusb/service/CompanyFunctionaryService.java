package com.bhp.opusb.service;

import com.bhp.opusb.domain.CompanyFunctionary;
import com.bhp.opusb.repository.CompanyFunctionaryRepository;
import com.bhp.opusb.service.dto.CompanyFunctionaryDTO;
import com.bhp.opusb.service.mapper.CompanyFunctionaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CompanyFunctionary}.
 */
@Service
@Transactional
public class CompanyFunctionaryService {

    private final Logger log = LoggerFactory.getLogger(CompanyFunctionaryService.class);

    private final CompanyFunctionaryRepository companyFunctionaryRepository;

    private final CompanyFunctionaryMapper companyFunctionaryMapper;

    public CompanyFunctionaryService(CompanyFunctionaryRepository companyFunctionaryRepository, CompanyFunctionaryMapper companyFunctionaryMapper) {
        this.companyFunctionaryRepository = companyFunctionaryRepository;
        this.companyFunctionaryMapper = companyFunctionaryMapper;
    }

    /**
     * Save a companyFunctionary.
     *
     * @param companyFunctionaryDTO the entity to save.
     * @return the persisted entity.
     */
    public CompanyFunctionaryDTO save(CompanyFunctionaryDTO companyFunctionaryDTO) {
        log.debug("Request to save CompanyFunctionary : {}", companyFunctionaryDTO);
        CompanyFunctionary companyFunctionary = companyFunctionaryMapper.toEntity(companyFunctionaryDTO);
        companyFunctionary = companyFunctionaryRepository.save(companyFunctionary);
        return companyFunctionaryMapper.toDto(companyFunctionary);
    }

    /**
     * Get all the companyFunctionaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyFunctionaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyFunctionaries");
        return companyFunctionaryRepository.findAll(pageable)
            .map(companyFunctionaryMapper::toDto);
    }

    /**
     * Get one companyFunctionary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompanyFunctionaryDTO> findOne(Long id) {
        log.debug("Request to get CompanyFunctionary : {}", id);
        return companyFunctionaryRepository.findById(id)
            .map(companyFunctionaryMapper::toDto);
    }

    /**
     * Delete the companyFunctionary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyFunctionary : {}", id);
        companyFunctionaryRepository.deleteById(id);
    }
}
