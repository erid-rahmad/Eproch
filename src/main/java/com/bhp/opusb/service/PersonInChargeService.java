package com.bhp.opusb.service;

import com.bhp.opusb.domain.PersonInCharge;
import com.bhp.opusb.repository.PersonInChargeRepository;
import com.bhp.opusb.service.dto.PersonInChargeDTO;
import com.bhp.opusb.service.mapper.PersonInChargeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PersonInCharge}.
 */
@Service
@Transactional
public class PersonInChargeService {

    private final Logger log = LoggerFactory.getLogger(PersonInChargeService.class);

    private final PersonInChargeRepository personInChargeRepository;

    private final PersonInChargeMapper personInChargeMapper;

    public PersonInChargeService(PersonInChargeRepository personInChargeRepository, PersonInChargeMapper personInChargeMapper) {
        this.personInChargeRepository = personInChargeRepository;
        this.personInChargeMapper = personInChargeMapper;
    }

    /**
     * Save a personInCharge.
     *
     * @param personInChargeDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonInChargeDTO save(PersonInChargeDTO personInChargeDTO) {
        log.debug("Request to save PersonInCharge : {}", personInChargeDTO);
        PersonInCharge personInCharge = personInChargeMapper.toEntity(personInChargeDTO);
        personInCharge = personInChargeRepository.save(personInCharge);
        return personInChargeMapper.toDto(personInCharge);
    }

    /**
     * Get all the personInCharges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonInChargeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonInCharges");
        return personInChargeRepository.findAll(pageable)
            .map(personInChargeMapper::toDto);
    }

    /**
     * Get one personInCharge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonInChargeDTO> findOne(Long id) {
        log.debug("Request to get PersonInCharge : {}", id);
        return personInChargeRepository.findById(id)
            .map(personInChargeMapper::toDto);
    }

    /**
     * Delete the personInCharge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonInCharge : {}", id);
        personInChargeRepository.deleteById(id);
    }
}
