package com.bhp.opusb.service;

import com.bhp.opusb.domain.CRegistrationDocType;
import com.bhp.opusb.repository.CRegistrationDocTypeRepository;
import com.bhp.opusb.service.dto.CRegistrationDocTypeDTO;
import com.bhp.opusb.service.mapper.CRegistrationDocTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CRegistrationDocType}.
 */
@Service
@Transactional
public class CRegistrationDocTypeService {

    private final Logger log = LoggerFactory.getLogger(CRegistrationDocTypeService.class);

    private final CRegistrationDocTypeRepository cRegistrationDocTypeRepository;

    private final CRegistrationDocTypeMapper cRegistrationDocTypeMapper;

    public CRegistrationDocTypeService(CRegistrationDocTypeRepository cRegistrationDocTypeRepository, CRegistrationDocTypeMapper cRegistrationDocTypeMapper) {
        this.cRegistrationDocTypeRepository = cRegistrationDocTypeRepository;
        this.cRegistrationDocTypeMapper = cRegistrationDocTypeMapper;
    }

    /**
     * Save a cRegistrationDocType.
     *
     * @param cRegistrationDocTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CRegistrationDocTypeDTO save(CRegistrationDocTypeDTO cRegistrationDocTypeDTO) {
        log.debug("Request to save CRegistrationDocType : {}", cRegistrationDocTypeDTO);
        CRegistrationDocType cRegistrationDocType = cRegistrationDocTypeMapper.toEntity(cRegistrationDocTypeDTO);
        cRegistrationDocType = cRegistrationDocTypeRepository.save(cRegistrationDocType);
        return cRegistrationDocTypeMapper.toDto(cRegistrationDocType);
    }

    /**
     * Get all the cRegistrationDocTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CRegistrationDocTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CRegistrationDocTypes");
        return cRegistrationDocTypeRepository.findAll(pageable)
            .map(cRegistrationDocTypeMapper::toDto);
    }

    /**
     * Get one cRegistrationDocType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CRegistrationDocTypeDTO> findOne(Long id) {
        log.debug("Request to get CRegistrationDocType : {}", id);
        return cRegistrationDocTypeRepository.findById(id)
            .map(cRegistrationDocTypeMapper::toDto);
    }

    /**
     * Delete the cRegistrationDocType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CRegistrationDocType : {}", id);
        cRegistrationDocTypeRepository.deleteById(id);
    }
}
