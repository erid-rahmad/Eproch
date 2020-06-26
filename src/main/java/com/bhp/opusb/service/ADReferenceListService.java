package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADReferenceList;
import com.bhp.opusb.repository.ADReferenceListRepository;
import com.bhp.opusb.service.dto.ADReferenceListDTO;
import com.bhp.opusb.service.mapper.ADReferenceListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ADReferenceList}.
 */
@Service
@Transactional
public class ADReferenceListService {

    private final Logger log = LoggerFactory.getLogger(ADReferenceListService.class);

    private final ADReferenceListRepository aDReferenceListRepository;

    private final ADReferenceListMapper aDReferenceListMapper;

    public ADReferenceListService(ADReferenceListRepository aDReferenceListRepository, ADReferenceListMapper aDReferenceListMapper) {
        this.aDReferenceListRepository = aDReferenceListRepository;
        this.aDReferenceListMapper = aDReferenceListMapper;
    }

    /**
     * Save a aDReferenceList.
     *
     * @param aDReferenceListDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADReference.aDReferenceLists", allEntries = true)
    public ADReferenceListDTO save(ADReferenceListDTO aDReferenceListDTO) {
        log.debug("Request to save ADReferenceList : {}", aDReferenceListDTO);
        ADReferenceList aDReferenceList = aDReferenceListMapper.toEntity(aDReferenceListDTO);
        aDReferenceList = aDReferenceListRepository.save(aDReferenceList);
        return aDReferenceListMapper.toDto(aDReferenceList);
    }

    /**
     * Get all the aDReferenceLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADReferenceListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADReferenceLists");
        return aDReferenceListRepository.findAll(pageable)
            .map(aDReferenceListMapper::toDto);
    }

    /**
     * Get one aDReferenceList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADReferenceListDTO> findOne(Long id) {
        log.debug("Request to get ADReferenceList : {}", id);
        return aDReferenceListRepository.findById(id)
            .map(aDReferenceListMapper::toDto);
    }

    /**
     * Delete the aDReferenceList by id.
     *
     * @param id the id of the entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADReference.aDReferenceLists", allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete ADReferenceList : {}", id);
        aDReferenceListRepository.deleteById(id);
    }
}
