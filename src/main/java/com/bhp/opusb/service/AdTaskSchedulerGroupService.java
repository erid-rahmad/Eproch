package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdTaskSchedulerGroup;
import com.bhp.opusb.repository.AdTaskSchedulerGroupRepository;
import com.bhp.opusb.service.dto.AdTaskSchedulerGroupDTO;
import com.bhp.opusb.service.mapper.AdTaskSchedulerGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdTaskSchedulerGroup}.
 */
@Service
@Transactional
public class AdTaskSchedulerGroupService {

    private final Logger log = LoggerFactory.getLogger(AdTaskSchedulerGroupService.class);

    private final AdTaskSchedulerGroupRepository adTaskSchedulerGroupRepository;

    private final AdTaskSchedulerGroupMapper adTaskSchedulerGroupMapper;

    public AdTaskSchedulerGroupService(AdTaskSchedulerGroupRepository adTaskSchedulerGroupRepository, AdTaskSchedulerGroupMapper adTaskSchedulerGroupMapper) {
        this.adTaskSchedulerGroupRepository = adTaskSchedulerGroupRepository;
        this.adTaskSchedulerGroupMapper = adTaskSchedulerGroupMapper;
    }

    /**
     * Save a adTaskSchedulerGroup.
     *
     * @param adTaskSchedulerGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public AdTaskSchedulerGroupDTO save(AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO) {
        log.debug("Request to save AdTaskSchedulerGroup : {}", adTaskSchedulerGroupDTO);
        AdTaskSchedulerGroup adTaskSchedulerGroup = adTaskSchedulerGroupMapper.toEntity(adTaskSchedulerGroupDTO);
        adTaskSchedulerGroup = adTaskSchedulerGroupRepository.save(adTaskSchedulerGroup);
        return adTaskSchedulerGroupMapper.toDto(adTaskSchedulerGroup);
    }

    /**
     * Get all the adTaskSchedulerGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskSchedulerGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdTaskSchedulerGroups");
        return adTaskSchedulerGroupRepository.findAll(pageable)
            .map(adTaskSchedulerGroupMapper::toDto);
    }

    /**
     * Get one adTaskSchedulerGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdTaskSchedulerGroupDTO> findOne(Long id) {
        log.debug("Request to get AdTaskSchedulerGroup : {}", id);
        return adTaskSchedulerGroupRepository.findById(id)
            .map(adTaskSchedulerGroupMapper::toDto);
    }

    /**
     * Delete the adTaskSchedulerGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdTaskSchedulerGroup : {}", id);
        adTaskSchedulerGroupRepository.deleteById(id);
    }
}
