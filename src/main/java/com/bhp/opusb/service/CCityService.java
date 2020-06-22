package com.bhp.opusb.service;

import com.bhp.opusb.domain.CCity;
import com.bhp.opusb.repository.CCityRepository;
import com.bhp.opusb.service.dto.CCityDTO;
import com.bhp.opusb.service.mapper.CCityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CCity}.
 */
@Service
@Transactional
public class CCityService {

    private final Logger log = LoggerFactory.getLogger(CCityService.class);

    private final CCityRepository cCityRepository;

    private final CCityMapper cCityMapper;

    public CCityService(CCityRepository cCityRepository, CCityMapper cCityMapper) {
        this.cCityRepository = cCityRepository;
        this.cCityMapper = cCityMapper;
    }

    /**
     * Save a cCity.
     *
     * @param cCityDTO the entity to save.
     * @return the persisted entity.
     */
    public CCityDTO save(CCityDTO cCityDTO) {
        log.debug("Request to save CCity : {}", cCityDTO);
        CCity cCity = cCityMapper.toEntity(cCityDTO);
        cCity = cCityRepository.save(cCity);
        return cCityMapper.toDto(cCity);
    }

    /**
     * Get all the cCities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CCityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CCities");
        return cCityRepository.findAll(pageable)
            .map(cCityMapper::toDto);
    }

    /**
     * Get one cCity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CCityDTO> findOne(Long id) {
        log.debug("Request to get CCity : {}", id);
        return cCityRepository.findById(id)
            .map(cCityMapper::toDto);
    }

    /**
     * Delete the cCity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CCity : {}", id);
        cCityRepository.deleteById(id);
    }
}
