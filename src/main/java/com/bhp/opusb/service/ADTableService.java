package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.repository.ADTableRepository;
import com.bhp.opusb.service.dto.ADTableDTO;
import com.bhp.opusb.service.mapper.ADTableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ADTable}.
 */
@Service
@Transactional
public class ADTableService {

    private final Logger log = LoggerFactory.getLogger(ADTableService.class);

    private final ADTableRepository aDTableRepository;

    private final ADTableMapper aDTableMapper;

    public ADTableService(ADTableRepository aDTableRepository, ADTableMapper aDTableMapper) {
        this.aDTableRepository = aDTableRepository;
        this.aDTableMapper = aDTableMapper;
    }

    /**
     * Save a aDTable.
     *
     * @param aDTableDTO the entity to save.
     * @return the persisted entity.
     */
    public ADTableDTO save(ADTableDTO aDTableDTO) {
        log.debug("Request to save ADTable : {}", aDTableDTO);
        ADTable aDTable = aDTableMapper.toEntity(aDTableDTO);
        aDTable = aDTableRepository.save(aDTable);
        return aDTableMapper.toDto(aDTable);
    }

    /**
     * Get all the aDTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADTableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADTables");
        return aDTableRepository.findAll(pageable)
            .map(aDTableMapper::toDto);
    }

    /**
     * Get one aDTable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADTableDTO> findOne(Long id) {
        log.debug("Request to get ADTable : {}", id);
        return aDTableRepository.findById(id)
            .map(aDTableMapper::toDto);
    }

    /**
     * Delete the aDTable by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ADTable : {}", id);
        aDTableRepository.deleteById(id);
    }
}
