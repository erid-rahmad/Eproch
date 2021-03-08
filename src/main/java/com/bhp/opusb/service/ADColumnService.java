package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.repository.ADColumnRepository;
import com.bhp.opusb.service.dto.ADColumnDTO;
import com.bhp.opusb.service.mapper.ADColumnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ADColumn}.
 */
@Service
@Transactional
public class ADColumnService {

    private final Logger log = LoggerFactory.getLogger(ADColumnService.class);

    private final ADColumnRepository aDColumnRepository;

    private final ADColumnMapper aDColumnMapper;

    public ADColumnService(ADColumnRepository aDColumnRepository, ADColumnMapper aDColumnMapper) {
        this.aDColumnRepository = aDColumnRepository;
        this.aDColumnMapper = aDColumnMapper;
    }

    /**
     * Save a aDColumn.
     *
     * @param aDColumnDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADTable.aDColumns", allEntries = true)
    public ADColumnDTO save(ADColumnDTO aDColumnDTO) {
        log.debug("Request to save ADColumn : {}", aDColumnDTO);
        ADColumn aDColumn = aDColumnMapper.toEntity(aDColumnDTO);
        aDColumn = aDColumnRepository.save(aDColumn);
        return aDColumnMapper.toDto(aDColumn);
    }

    /**
     * Get all the aDColumns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADColumnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADColumns");
        return aDColumnRepository.findAll(pageable)
            .map(aDColumnMapper::toDto);
    }

    /**
     * Get one aDColumn by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADColumnDTO> findOne(Long id) {
        log.debug("Request to get ADColumn : {}", id);
        return aDColumnRepository.findById(id)
            .map(aDColumnMapper::toDto);
    }

    /**
     * Delete the aDColumn by id.
     *
     * @param id the id of the entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADTable.aDColumns", allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete ADColumn : {}", id);
        aDColumnRepository.deleteById(id);
    }
}
