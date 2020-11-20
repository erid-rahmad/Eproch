package com.bhp.opusb.service;

import java.util.Optional;

import com.bhp.opusb.domain.COrganization;
import com.bhp.opusb.repository.COrganizationRepository;
import com.bhp.opusb.service.dto.COrganizationDTO;
import com.bhp.opusb.service.mapper.COrganizationMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link COrganization}.
 */
@Service
@Transactional
public class COrganizationService {

    private final Logger log = LoggerFactory.getLogger(COrganizationService.class);

    private final COrganizationRepository cOrganizationRepository;

    private final COrganizationMapper cOrganizationMapper;

    public COrganizationService(COrganizationRepository cOrganizationRepository, COrganizationMapper cOrganizationMapper) {
        this.cOrganizationRepository = cOrganizationRepository;
        this.cOrganizationMapper = cOrganizationMapper;
    }

    /**
     * Save a cOrganization.
     *
     * @param cOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    public COrganizationDTO save(COrganizationDTO cOrganizationDTO) {
        log.debug("Request to save COrganization : {}", cOrganizationDTO);
        COrganization cOrganization = cOrganizationMapper.toEntity(cOrganizationDTO);
        cOrganization = cOrganizationRepository.save(cOrganization);
        return cOrganizationMapper.toDto(cOrganization);
    }

    /**
     * Get all the cOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<COrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all COrganizations");
        return cOrganizationRepository.findAll(pageable)
            .map(cOrganizationMapper::toDto);
    }

    /**
     * Get one cOrganization by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<COrganizationDTO> findOne(Long id) {
        log.debug("Request to get COrganization : {}", id);
        return cOrganizationRepository.findById(id)
            .map(cOrganizationMapper::toDto);
    }

    /**
     * Delete the cOrganization by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete COrganization : {}", id);
        cOrganizationRepository.deleteById(id);
    }
}
