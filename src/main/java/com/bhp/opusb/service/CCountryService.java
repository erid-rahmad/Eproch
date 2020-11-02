package com.bhp.opusb.service;

import com.bhp.opusb.domain.CCountry;
import com.bhp.opusb.repository.CCountryRepository;
import com.bhp.opusb.service.dto.CCountryDTO;
import com.bhp.opusb.service.mapper.CCountryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CCountry}.
 */
@Service
@Transactional
public class CCountryService {

    private final Logger log = LoggerFactory.getLogger(CCountryService.class);

    private final CCountryRepository cCountryRepository;

    private final CCountryMapper cCountryMapper;

    public CCountryService(CCountryRepository cCountryRepository, CCountryMapper cCountryMapper) {
        this.cCountryRepository = cCountryRepository;
        this.cCountryMapper = cCountryMapper;
    }

    /**
     * Save a cCountry.
     *
     * @param cCountryDTO the entity to save.
     * @return the persisted entity.
     */
    public CCountryDTO save(CCountryDTO cCountryDTO) {
        log.debug("Request to save CCountry : {}", cCountryDTO);
        CCountry cCountry = cCountryMapper.toEntity(cCountryDTO);
        cCountry = cCountryRepository.save(cCountry);
        return cCountryMapper.toDto(cCountry);
    }

    /**
     * Get all the cCountries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CCountryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CCountries");
        return cCountryRepository.findAll(pageable)
            .map(cCountryMapper::toDto);
    }

    /**
     * Get one cCountry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CCountryDTO> findOne(Long id) {
        log.debug("Request to get CCountry : {}", id);
        return cCountryRepository.findById(id)
            .map(cCountryMapper::toDto);
    }

    /**
     * Delete the cCountry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CCountry : {}", id);
        cCountryRepository.deleteById(id);
    }
}
