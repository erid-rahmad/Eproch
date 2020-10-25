package com.bhp.opusb.service;

import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.repository.CDocumentTypeRepository;
import com.bhp.opusb.service.dto.CDocumentTypeDTO;
import com.bhp.opusb.service.mapper.CDocumentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CDocumentType}.
 */
@Service
@Transactional
public class CDocumentTypeService {

    private final Logger log = LoggerFactory.getLogger(CDocumentTypeService.class);

    private final CDocumentTypeRepository cDocumentTypeRepository;

    private final CDocumentTypeMapper cDocumentTypeMapper;

    public CDocumentTypeService(CDocumentTypeRepository cDocumentTypeRepository, CDocumentTypeMapper cDocumentTypeMapper) {
        this.cDocumentTypeRepository = cDocumentTypeRepository;
        this.cDocumentTypeMapper = cDocumentTypeMapper;
    }

    /**
     * Save a cDocumentType.
     *
     * @param cDocumentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CDocumentTypeDTO save(CDocumentTypeDTO cDocumentTypeDTO) {
        log.debug("Request to save CDocumentType : {}", cDocumentTypeDTO);
        CDocumentType cDocumentType = cDocumentTypeMapper.toEntity(cDocumentTypeDTO);
        cDocumentType = cDocumentTypeRepository.save(cDocumentType);
        return cDocumentTypeMapper.toDto(cDocumentType);
    }

    /**
     * Get all the cDocumentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CDocumentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CDocumentTypes");
        return cDocumentTypeRepository.findAll(pageable)
            .map(cDocumentTypeMapper::toDto);
    }

    /**
     * Get one cDocumentType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CDocumentTypeDTO> findOne(Long id) {
        log.debug("Request to get CDocumentType : {}", id);
        return cDocumentTypeRepository.findById(id)
            .map(cDocumentTypeMapper::toDto);
    }

    /**
     * Delete the cDocumentType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CDocumentType : {}", id);
        cDocumentTypeRepository.deleteById(id);
    }
}
