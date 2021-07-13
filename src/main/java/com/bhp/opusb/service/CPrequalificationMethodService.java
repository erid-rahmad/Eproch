package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPrequalificationMethod;
import com.bhp.opusb.repository.CPrequalificationMethodRepository;
import com.bhp.opusb.service.dto.CPrequalificationMethodDTO;
import com.bhp.opusb.service.mapper.CPrequalificationMethodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPrequalificationMethod}.
 */
@Service
@Transactional
public class CPrequalificationMethodService {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationMethodService.class);

    private final CPrequalificationMethodRepository cPrequalificationMethodRepository;

    private final CPrequalificationMethodMapper cPrequalificationMethodMapper;

    public CPrequalificationMethodService(CPrequalificationMethodRepository cPrequalificationMethodRepository, CPrequalificationMethodMapper cPrequalificationMethodMapper) {
        this.cPrequalificationMethodRepository = cPrequalificationMethodRepository;
        this.cPrequalificationMethodMapper = cPrequalificationMethodMapper;
    }

    /**
     * Save a cPrequalificationMethod.
     *
     * @param cPrequalificationMethodDTO the entity to save.
     * @return the persisted entity.
     */
    public CPrequalificationMethodDTO save(CPrequalificationMethodDTO cPrequalificationMethodDTO) {
        log.debug("Request to save CPrequalificationMethod : {}", cPrequalificationMethodDTO);
        CPrequalificationMethod cPrequalificationMethod = cPrequalificationMethodMapper.toEntity(cPrequalificationMethodDTO);
        cPrequalificationMethod = cPrequalificationMethodRepository.save(cPrequalificationMethod);
        return cPrequalificationMethodMapper.toDto(cPrequalificationMethod);
    }

    /**
     * Get all the cPrequalificationMethods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalificationMethodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPrequalificationMethods");
        return cPrequalificationMethodRepository.findAll(pageable)
            .map(cPrequalificationMethodMapper::toDto);
    }

    /**
     * Get one cPrequalificationMethod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPrequalificationMethodDTO> findOne(Long id) {
        log.debug("Request to get CPrequalificationMethod : {}", id);
        return cPrequalificationMethodRepository.findById(id)
            .map(cPrequalificationMethodMapper::toDto);
    }

    /**
     * Delete the cPrequalificationMethod by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPrequalificationMethod : {}", id);
        cPrequalificationMethodRepository.deleteById(id);
    }
}
