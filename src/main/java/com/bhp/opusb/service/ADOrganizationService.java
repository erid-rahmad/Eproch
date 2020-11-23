package com.bhp.opusb.service;

import java.util.Optional;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.ADOrganizationRepository;
import com.bhp.opusb.service.dto.ADOrganizationDTO;
import com.bhp.opusb.service.mapper.ADOrganizationMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ADOrganization}.
 */
@Service
@Transactional
public class ADOrganizationService {

    private final Logger log = LoggerFactory.getLogger(ADOrganizationService.class);

    private final ADOrganizationRepository aDOrganizationRepository;

    private final ADOrganizationMapper aDOrganizationMapper;

    private final ApplicationProperties applicationProperties;

    public ADOrganizationService(ADOrganizationRepository aDOrganizationRepository, ADOrganizationMapper aDOrganizationMapper,
            ApplicationProperties applicationProperties) {
        this.aDOrganizationRepository = aDOrganizationRepository;
        this.aDOrganizationMapper = aDOrganizationMapper;
        this.applicationProperties = applicationProperties;
    }

    /**
     * Save a aDOrganization.
     *
     * @param aDOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    public ADOrganizationDTO save(ADOrganizationDTO aDOrganizationDTO) {
        log.debug("Request to save ADOrganization : {}", aDOrganizationDTO);
        ADOrganization aDOrganization = aDOrganizationMapper.toEntity(aDOrganizationDTO);
        aDOrganization = aDOrganizationRepository.save(aDOrganization);
        return aDOrganizationMapper.toDto(aDOrganization);
    }

    /**
     * Get all the aDOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADOrganizations");
        return aDOrganizationRepository.findAll(pageable)
            .map(aDOrganizationMapper::toDto);
    }

    /**
     * Get one aDOrganization by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADOrganizationDTO> findOne(Long id) {
        log.debug("Request to get ADOrganization : {}", id);
        return aDOrganizationRepository.findById(id)
            .map(aDOrganizationMapper::toDto);
    }

    /**
     * Get an organization by the given code, or create a new if it doesn't exist.
     */
    public ADOrganization findOrCreate(String code) {
        return aDOrganizationRepository.findFirstByCode(code)
            .orElseGet(() -> {
                ADOrganization org = new ADOrganization();
                org.active(true)
                    .code(code)
                    .name(code);

                return aDOrganizationRepository.save(org);
            });
    }

    /**
     * Delete the aDOrganization by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ADOrganization : {}", id);
        aDOrganizationRepository.deleteById(id);
    }

    public ADOrganization getDefaultOrganization() {
        ADOrganization organization = new ADOrganization();
        organization.setId(applicationProperties.getDefaultOrganizationId());
        return organization;
    }
}
