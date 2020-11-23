package com.bhp.opusb.service;

import java.util.Optional;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.domain.CElementValue;
import com.bhp.opusb.repository.CElementValueRepository;
import com.bhp.opusb.service.dto.CElementValueDTO;
import com.bhp.opusb.service.mapper.CElementValueMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CElementValue}.
 */
@Service
@Transactional
public class CElementValueService {

    private final Logger log = LoggerFactory.getLogger(CElementValueService.class);

    private final CElementValueRepository cElementValueRepository;

    private final CElementValueMapper cElementValueMapper;

    public CElementValueService(CElementValueRepository cElementValueRepository, CElementValueMapper cElementValueMapper) {
        this.cElementValueRepository = cElementValueRepository;
        this.cElementValueMapper = cElementValueMapper;
    }

    /**
     * Save a cElementValue.
     *
     * @param cElementValueDTO the entity to save.
     * @return the persisted entity.
     */
    public CElementValueDTO save(CElementValueDTO cElementValueDTO) {
        log.debug("Request to save CElementValue : {}", cElementValueDTO);
        CElementValue cElementValue = cElementValueMapper.toEntity(cElementValueDTO);
        cElementValue = cElementValueRepository.save(cElementValue);
        return cElementValueMapper.toDto(cElementValue);
    }

    /**
     * Get all the cElementValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CElementValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CElementValues");
        return cElementValueRepository.findAll(pageable)
            .map(cElementValueMapper::toDto);
    }

    /**
     * Get one cElementValue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CElementValueDTO> findOne(Long id) {
        log.debug("Request to get CElementValue : {}", id);
        return cElementValueRepository.findById(id)
            .map(cElementValueMapper::toDto);
    }

    /**
     * Delete the cElementValue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CElementValue : {}", id);
        cElementValueRepository.deleteById(id);
    }
}
