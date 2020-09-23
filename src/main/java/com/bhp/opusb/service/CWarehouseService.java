package com.bhp.opusb.service;

import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.repository.CWarehouseRepository;
import com.bhp.opusb.service.dto.CWarehouseDTO;
import com.bhp.opusb.service.mapper.CWarehouseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CWarehouse}.
 */
@Service
@Transactional
public class CWarehouseService {

    private final Logger log = LoggerFactory.getLogger(CWarehouseService.class);

    private final CWarehouseRepository cWarehouseRepository;

    private final CWarehouseMapper cWarehouseMapper;

    public CWarehouseService(CWarehouseRepository cWarehouseRepository, CWarehouseMapper cWarehouseMapper) {
        this.cWarehouseRepository = cWarehouseRepository;
        this.cWarehouseMapper = cWarehouseMapper;
    }

    /**
     * Save a cWarehouse.
     *
     * @param cWarehouseDTO the entity to save.
     * @return the persisted entity.
     */
    public CWarehouseDTO save(CWarehouseDTO cWarehouseDTO) {
        log.debug("Request to save CWarehouse : {}", cWarehouseDTO);
        CWarehouse cWarehouse = cWarehouseMapper.toEntity(cWarehouseDTO);
        cWarehouse = cWarehouseRepository.save(cWarehouse);
        return cWarehouseMapper.toDto(cWarehouse);
    }

    /**
     * Get all the cWarehouses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CWarehouseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CWarehouses");
        return cWarehouseRepository.findAll(pageable)
            .map(cWarehouseMapper::toDto);
    }

    /**
     * Get one cWarehouse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CWarehouseDTO> findOne(Long id) {
        log.debug("Request to get CWarehouse : {}", id);
        return cWarehouseRepository.findById(id)
            .map(cWarehouseMapper::toDto);
    }

    /**
     * Delete the cWarehouse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CWarehouse : {}", id);
        cWarehouseRepository.deleteById(id);
    }
}
