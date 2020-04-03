package com.bhp.opusb.service;

import com.bhp.opusb.domain.ReferenceList;
import com.bhp.opusb.repository.ReferenceListRepository;
import com.bhp.opusb.service.dto.ReferenceListDTO;
import com.bhp.opusb.service.mapper.ReferenceListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReferenceList}.
 */
@Service
@Transactional
public class ReferenceListService {

    private final Logger log = LoggerFactory.getLogger(ReferenceListService.class);

    private final ReferenceListRepository referenceListRepository;

    private final ReferenceListMapper referenceListMapper;

    public ReferenceListService(ReferenceListRepository referenceListRepository, ReferenceListMapper referenceListMapper) {
        this.referenceListRepository = referenceListRepository;
        this.referenceListMapper = referenceListMapper;
    }

    /**
     * Save a referenceList.
     *
     * @param referenceListDTO the entity to save.
     * @return the persisted entity.
     */
    public ReferenceListDTO save(ReferenceListDTO referenceListDTO) {
        log.debug("Request to save ReferenceList : {}", referenceListDTO);
        ReferenceList referenceList = referenceListMapper.toEntity(referenceListDTO);
        referenceList = referenceListRepository.save(referenceList);
        return referenceListMapper.toDto(referenceList);
    }

    /**
     * Get all the referenceLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ReferenceListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReferenceLists");
        return referenceListRepository.findAll(pageable)
            .map(referenceListMapper::toDto);
    }

    /**
     * Get one referenceList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ReferenceListDTO> findOne(Long id) {
        log.debug("Request to get ReferenceList : {}", id);
        return referenceListRepository.findById(id)
            .map(referenceListMapper::toDto);
    }

    /**
     * Delete the referenceList by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ReferenceList : {}", id);
        referenceListRepository.deleteById(id);
    }
}
