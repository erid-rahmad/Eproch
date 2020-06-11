package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADFieldGroup;
import com.bhp.opusb.repository.ADFieldGroupRepository;
import com.bhp.opusb.service.dto.ADFieldGroupDTO;
import com.bhp.opusb.service.mapper.ADFieldGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ADFieldGroup}.
 */
@Service
@Transactional
public class ADFieldGroupService {

    private final Logger log = LoggerFactory.getLogger(ADFieldGroupService.class);

    private final ADFieldGroupRepository aDFieldGroupRepository;

    private final ADFieldGroupMapper aDFieldGroupMapper;

    public ADFieldGroupService(ADFieldGroupRepository aDFieldGroupRepository, ADFieldGroupMapper aDFieldGroupMapper) {
        this.aDFieldGroupRepository = aDFieldGroupRepository;
        this.aDFieldGroupMapper = aDFieldGroupMapper;
    }

    /**
     * Save a aDFieldGroup.
     *
     * @param aDFieldGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public ADFieldGroupDTO save(ADFieldGroupDTO aDFieldGroupDTO) {
        log.debug("Request to save ADFieldGroup : {}", aDFieldGroupDTO);
        ADFieldGroup aDFieldGroup = aDFieldGroupMapper.toEntity(aDFieldGroupDTO);
        aDFieldGroup = aDFieldGroupRepository.save(aDFieldGroup);
        return aDFieldGroupMapper.toDto(aDFieldGroup);
    }

    /**
     * Get all the aDFieldGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADFieldGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADFieldGroups");
        return aDFieldGroupRepository.findAll(pageable)
            .map(aDFieldGroupMapper::toDto);
    }

    /**
     * Get one aDFieldGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADFieldGroupDTO> findOne(Long id) {
        log.debug("Request to get ADFieldGroup : {}", id);
        return aDFieldGroupRepository.findById(id)
            .map(aDFieldGroupMapper::toDto);
    }

    /**
     * Delete the aDFieldGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ADFieldGroup : {}", id);
        aDFieldGroupRepository.deleteById(id);
    }
}
