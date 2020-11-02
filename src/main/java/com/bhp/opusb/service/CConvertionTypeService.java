package com.bhp.opusb.service;

import com.bhp.opusb.domain.CConvertionType;
import com.bhp.opusb.repository.CConvertionTypeRepository;
import com.bhp.opusb.service.dto.CConvertionTypeDTO;
import com.bhp.opusb.service.mapper.CConvertionTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CConvertionType}.
 */
@Service
@Transactional
public class CConvertionTypeService {

    private final Logger log = LoggerFactory.getLogger(CConvertionTypeService.class);

    private final CConvertionTypeRepository cConvertionTypeRepository;

    private final CConvertionTypeMapper cConvertionTypeMapper;

    public CConvertionTypeService(CConvertionTypeRepository cConvertionTypeRepository, CConvertionTypeMapper cConvertionTypeMapper) {
        this.cConvertionTypeRepository = cConvertionTypeRepository;
        this.cConvertionTypeMapper = cConvertionTypeMapper;
    }

    /**
     * Save a cConvertionType.
     *
     * @param cConvertionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CConvertionTypeDTO save(CConvertionTypeDTO cConvertionTypeDTO) {
        log.debug("Request to save CConvertionType : {}", cConvertionTypeDTO);
        CConvertionType cConvertionType = cConvertionTypeMapper.toEntity(cConvertionTypeDTO);
        cConvertionType = cConvertionTypeRepository.save(cConvertionType);
        return cConvertionTypeMapper.toDto(cConvertionType);
    }

    /**
     * Get all the cConvertionTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CConvertionTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CConvertionTypes");
        return cConvertionTypeRepository.findAll(pageable)
            .map(cConvertionTypeMapper::toDto);
    }

    /**
     * Get one cConvertionType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CConvertionTypeDTO> findOne(Long id) {
        log.debug("Request to get CConvertionType : {}", id);
        return cConvertionTypeRepository.findById(id)
            .map(cConvertionTypeMapper::toDto);
    }

    /**
     * Delete the cConvertionType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CConvertionType : {}", id);
        cConvertionTypeRepository.deleteById(id);
    }
}
