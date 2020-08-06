package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPicBusinessCat;
import com.bhp.opusb.repository.CPicBusinessCatRepository;
import com.bhp.opusb.service.dto.CPicBusinessCatDTO;
import com.bhp.opusb.service.mapper.CPicBusinessCatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPicBusinessCat}.
 */
@Service
@Transactional
public class CPicBusinessCatService {

    private final Logger log = LoggerFactory.getLogger(CPicBusinessCatService.class);

    private final CPicBusinessCatRepository cPicBusinessCatRepository;

    private final CPicBusinessCatMapper cPicBusinessCatMapper;

    public CPicBusinessCatService(CPicBusinessCatRepository cPicBusinessCatRepository, CPicBusinessCatMapper cPicBusinessCatMapper) {
        this.cPicBusinessCatRepository = cPicBusinessCatRepository;
        this.cPicBusinessCatMapper = cPicBusinessCatMapper;
    }

    /**
     * Save a cPicBusinessCat.
     *
     * @param cPicBusinessCatDTO the entity to save.
     * @return the persisted entity.
     */
    public CPicBusinessCatDTO save(CPicBusinessCatDTO cPicBusinessCatDTO) {
        log.debug("Request to save CPicBusinessCat : {}", cPicBusinessCatDTO);
        CPicBusinessCat cPicBusinessCat = cPicBusinessCatMapper.toEntity(cPicBusinessCatDTO);
        cPicBusinessCat = cPicBusinessCatRepository.save(cPicBusinessCat);
        return cPicBusinessCatMapper.toDto(cPicBusinessCat);
    }

    /**
     * Get all the cPicBusinessCats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPicBusinessCatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPicBusinessCats");
        return cPicBusinessCatRepository.findAll(pageable)
            .map(cPicBusinessCatMapper::toDto);
    }

    /**
     * Get one cPicBusinessCat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPicBusinessCatDTO> findOne(Long id) {
        log.debug("Request to get CPicBusinessCat : {}", id);
        return cPicBusinessCatRepository.findById(id)
            .map(cPicBusinessCatMapper::toDto);
    }

    /**
     * Delete the cPicBusinessCat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPicBusinessCat : {}", id);
        cPicBusinessCatRepository.deleteById(id);
    }
}
