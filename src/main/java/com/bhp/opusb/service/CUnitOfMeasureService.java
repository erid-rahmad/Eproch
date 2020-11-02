package com.bhp.opusb.service;

import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.repository.CUnitOfMeasureRepository;
import com.bhp.opusb.service.dto.CUnitOfMeasureDTO;
import com.bhp.opusb.service.mapper.CUnitOfMeasureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CUnitOfMeasure}.
 */
@Service
@Transactional
public class CUnitOfMeasureService {

    private final Logger log = LoggerFactory.getLogger(CUnitOfMeasureService.class);

    private final CUnitOfMeasureRepository cUnitOfMeasureRepository;

    private final CUnitOfMeasureMapper cUnitOfMeasureMapper;

    public CUnitOfMeasureService(CUnitOfMeasureRepository cUnitOfMeasureRepository, CUnitOfMeasureMapper cUnitOfMeasureMapper) {
        this.cUnitOfMeasureRepository = cUnitOfMeasureRepository;
        this.cUnitOfMeasureMapper = cUnitOfMeasureMapper;
    }

    /**
     * Save a cUnitOfMeasure.
     *
     * @param cUnitOfMeasureDTO the entity to save.
     * @return the persisted entity.
     */
    public CUnitOfMeasureDTO save(CUnitOfMeasureDTO cUnitOfMeasureDTO) {
        log.debug("Request to save CUnitOfMeasure : {}", cUnitOfMeasureDTO);
        CUnitOfMeasure cUnitOfMeasure = cUnitOfMeasureMapper.toEntity(cUnitOfMeasureDTO);
        cUnitOfMeasure = cUnitOfMeasureRepository.save(cUnitOfMeasure);
        return cUnitOfMeasureMapper.toDto(cUnitOfMeasure);
    }

    /**
     * Get all the cUnitOfMeasures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CUnitOfMeasureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CUnitOfMeasures");
        return cUnitOfMeasureRepository.findAll(pageable)
            .map(cUnitOfMeasureMapper::toDto);
    }

    /**
     * Get one cUnitOfMeasure by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CUnitOfMeasureDTO> findOne(Long id) {
        log.debug("Request to get CUnitOfMeasure : {}", id);
        return cUnitOfMeasureRepository.findById(id)
            .map(cUnitOfMeasureMapper::toDto);
    }

    /**
     * Delete the cUnitOfMeasure by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CUnitOfMeasure : {}", id);
        cUnitOfMeasureRepository.deleteById(id);
    }
}
