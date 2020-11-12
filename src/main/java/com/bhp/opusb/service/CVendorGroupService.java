package com.bhp.opusb.service;

import com.bhp.opusb.domain.CVendorGroup;
import com.bhp.opusb.repository.CVendorGroupRepository;
import com.bhp.opusb.service.dto.CVendorGroupDTO;
import com.bhp.opusb.service.mapper.CVendorGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CVendorGroup}.
 */
@Service
@Transactional
public class CVendorGroupService {

    private final Logger log = LoggerFactory.getLogger(CVendorGroupService.class);

    private final CVendorGroupRepository cVendorGroupRepository;

    private final CVendorGroupMapper cVendorGroupMapper;

    public CVendorGroupService(CVendorGroupRepository cVendorGroupRepository, CVendorGroupMapper cVendorGroupMapper) {
        this.cVendorGroupRepository = cVendorGroupRepository;
        this.cVendorGroupMapper = cVendorGroupMapper;
    }

    /**
     * Save a cVendorGroup.
     *
     * @param cVendorGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public CVendorGroupDTO save(CVendorGroupDTO cVendorGroupDTO) {
        log.debug("Request to save CVendorGroup : {}", cVendorGroupDTO);
        CVendorGroup cVendorGroup = cVendorGroupMapper.toEntity(cVendorGroupDTO);
        cVendorGroup = cVendorGroupRepository.save(cVendorGroup);
        return cVendorGroupMapper.toDto(cVendorGroup);
    }

    /**
     * Get all the cVendorGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CVendorGroups");
        return cVendorGroupRepository.findAll(pageable)
            .map(cVendorGroupMapper::toDto);
    }

    /**
     * Get one cVendorGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CVendorGroupDTO> findOne(Long id) {
        log.debug("Request to get CVendorGroup : {}", id);
        return cVendorGroupRepository.findById(id)
            .map(cVendorGroupMapper::toDto);
    }

    /**
     * Delete the cVendorGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CVendorGroup : {}", id);
        cVendorGroupRepository.deleteById(id);
    }
}
