package com.bhp.opusb.service;

import com.bhp.opusb.domain.CBiddingType;
import com.bhp.opusb.repository.CBiddingTypeRepository;
import com.bhp.opusb.service.dto.CBiddingTypeDTO;
import com.bhp.opusb.service.mapper.CBiddingTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CBiddingType}.
 */
@Service
@Transactional
public class CBiddingTypeService {

    private final Logger log = LoggerFactory.getLogger(CBiddingTypeService.class);

    private final CBiddingTypeRepository cBiddingTypeRepository;

    private final CBiddingTypeMapper cBiddingTypeMapper;

    public CBiddingTypeService(CBiddingTypeRepository cBiddingTypeRepository, CBiddingTypeMapper cBiddingTypeMapper) {
        this.cBiddingTypeRepository = cBiddingTypeRepository;
        this.cBiddingTypeMapper = cBiddingTypeMapper;
    }

    /**
     * Save a cBiddingType.
     *
     * @param cBiddingTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public CBiddingTypeDTO save(CBiddingTypeDTO cBiddingTypeDTO) {
        log.debug("Request to save CBiddingType : {}", cBiddingTypeDTO);
        CBiddingType cBiddingType = cBiddingTypeMapper.toEntity(cBiddingTypeDTO);
        cBiddingType = cBiddingTypeRepository.save(cBiddingType);
        return cBiddingTypeMapper.toDto(cBiddingType);
    }

    /**
     * Get all the cBiddingTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CBiddingTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CBiddingTypes");
        return cBiddingTypeRepository.findAll(pageable)
            .map(cBiddingTypeMapper::toDto);
    }

    /**
     * Get one cBiddingType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CBiddingTypeDTO> findOne(Long id) {
        log.debug("Request to get CBiddingType : {}", id);
        return cBiddingTypeRepository.findById(id)
            .map(cBiddingTypeMapper::toDto);
    }

    /**
     * Delete the cBiddingType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CBiddingType : {}", id);
        cBiddingTypeRepository.deleteById(id);
    }
}
