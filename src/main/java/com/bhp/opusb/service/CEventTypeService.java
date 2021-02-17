package com.bhp.opusb.service;

import com.bhp.opusb.domain.CEventType;
import com.bhp.opusb.repository.CEventTypeRepository;
import com.bhp.opusb.service.dto.CEventTypeDTO;
import com.bhp.opusb.service.mapper.CEventTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CEventType}.
 */
@Service
@Transactional
public class CEventTypeService {

    private final Logger log = LoggerFactory.getLogger(CEventTypeService.class);

    private final CEventTypeRepository cEventTypeRepository;

    private final CEventTypeMapper cEventTypeMapper;

    public CEventTypeService(CEventTypeRepository cEventTypeRepository, CEventTypeMapper cEventTypeMapper) {
        this.cEventTypeRepository = cEventTypeRepository;
        this.cEventTypeMapper = cEventTypeMapper;
    }

    /**
     * Save a cEventType.
     *
     * @param cEventTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CEventTypeDTO save(CEventTypeDTO cEventTypeDTO) {
        log.debug("Request to save CEventType : {}", cEventTypeDTO);
        CEventType cEventType = cEventTypeMapper.toEntity(cEventTypeDTO);
        cEventType = cEventTypeRepository.save(cEventType);
        return cEventTypeMapper.toDto(cEventType);
    }

    /**
     * Get all the cEventTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEventTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CEventTypes");
        return cEventTypeRepository.findAll(pageable)
            .map(cEventTypeMapper::toDto);
    }

    /**
     * Get one cEventType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CEventTypeDTO> findOne(Long id) {
        log.debug("Request to get CEventType : {}", id);
        return cEventTypeRepository.findById(id)
            .map(cEventTypeMapper::toDto);
    }

    /**
     * Delete the cEventType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CEventType : {}", id);
        cEventTypeRepository.deleteById(id);
    }
}
