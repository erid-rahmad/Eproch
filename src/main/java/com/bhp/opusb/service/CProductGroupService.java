package com.bhp.opusb.service;

import com.bhp.opusb.domain.CProductGroup;
import com.bhp.opusb.repository.CProductGroupRepository;
import com.bhp.opusb.service.dto.CProductGroupDTO;
import com.bhp.opusb.service.mapper.CProductGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CProductGroup}.
 */
@Service
@Transactional
public class CProductGroupService {

    private final Logger log = LoggerFactory.getLogger(CProductGroupService.class);

    private final CProductGroupRepository cProductGroupRepository;

    private final CProductGroupMapper cProductGroupMapper;

    public CProductGroupService(CProductGroupRepository cProductGroupRepository, CProductGroupMapper cProductGroupMapper) {
        this.cProductGroupRepository = cProductGroupRepository;
        this.cProductGroupMapper = cProductGroupMapper;
    }

    /**
     * Save a cProductGroup.
     *
     * @param cProductGroupDTO the entity to save.
     * @return the persisted entity.
     */
    public CProductGroupDTO save(CProductGroupDTO cProductGroupDTO) {
        log.debug("Request to save CProductGroup : {}", cProductGroupDTO);
        CProductGroup cProductGroup = cProductGroupMapper.toEntity(cProductGroupDTO);
        cProductGroup = cProductGroupRepository.save(cProductGroup);
        return cProductGroupMapper.toDto(cProductGroup);
    }

    /**
     * Get all the cProductGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CProductGroups");
        return cProductGroupRepository.findAll(pageable)
            .map(cProductGroupMapper::toDto);
    }

    /**
     * Get one cProductGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CProductGroupDTO> findOne(Long id) {
        log.debug("Request to get CProductGroup : {}", id);
        return cProductGroupRepository.findById(id)
            .map(cProductGroupMapper::toDto);
    }

    /**
     * Delete the cProductGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CProductGroup : {}", id);
        cProductGroupRepository.deleteById(id);
    }
}
