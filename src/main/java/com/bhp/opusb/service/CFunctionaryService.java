package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CFunctionary;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.CFunctionaryRepository;
import com.bhp.opusb.service.dto.CFunctionaryDTO;
import com.bhp.opusb.service.mapper.CFunctionaryMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CFunctionary}.
 */
@Service
@Transactional
public class CFunctionaryService {

    private final Logger log = LoggerFactory.getLogger(CFunctionaryService.class);

    private final CFunctionaryRepository cFunctionaryRepository;

    private final CFunctionaryMapper cFunctionaryMapper;

    public CFunctionaryService(CFunctionaryRepository cFunctionaryRepository, CFunctionaryMapper cFunctionaryMapper) {
        this.cFunctionaryRepository = cFunctionaryRepository;
        this.cFunctionaryMapper = cFunctionaryMapper;
    }

    /**
     * Save a cFunctionary.
     *
     * @param cFunctionaryDTO the entity to save.
     * @return the persisted entity.
     */
    public CFunctionaryDTO save(CFunctionaryDTO cFunctionaryDTO) {
        log.debug("Request to save CFunctionary : {}", cFunctionaryDTO);
        CFunctionary cFunctionary = cFunctionaryMapper.toEntity(cFunctionaryDTO);
        cFunctionary = cFunctionaryRepository.save(cFunctionary);
        return cFunctionaryMapper.toDto(cFunctionary);
    }

    public List<CFunctionaryDTO> saveAll(List<CFunctionaryDTO> functionaryDTOs, CVendor vendor, ADOrganization organization) {
        List<CFunctionary> functionaries = functionaryDTOs.stream()
            .map(functionary
                -> cFunctionaryMapper.toEntity(functionary)
                    .active(true)
                    .adOrganization(organization)
                    .vendor(vendor)
            )
            .collect(Collectors.toList());

        return cFunctionaryRepository.saveAll(functionaries)
            .stream()
            .map(cFunctionaryMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all the cFunctionaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CFunctionaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CFunctionaries");
        return cFunctionaryRepository.findAll(pageable)
            .map(cFunctionaryMapper::toDto);
    }

    /**
     * Get one cFunctionary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CFunctionaryDTO> findOne(Long id) {
        log.debug("Request to get CFunctionary : {}", id);
        return cFunctionaryRepository.findById(id)
            .map(cFunctionaryMapper::toDto);
    }

    /**
     * Delete the cFunctionary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CFunctionary : {}", id);
        cFunctionaryRepository.deleteById(id);
    }
}
