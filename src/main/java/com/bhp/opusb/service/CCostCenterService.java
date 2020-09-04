package com.bhp.opusb.service;

import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.repository.CCostCenterRepository;
import com.bhp.opusb.service.dto.CCostCenterDTO;
import com.bhp.opusb.service.mapper.CCostCenterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CCostCenter}.
 */
@Service
@Transactional
public class CCostCenterService {

    private final Logger log = LoggerFactory.getLogger(CCostCenterService.class);

    private final CCostCenterRepository cCostCenterRepository;

    private final CCostCenterMapper cCostCenterMapper;

    public CCostCenterService(CCostCenterRepository cCostCenterRepository, CCostCenterMapper cCostCenterMapper) {
        this.cCostCenterRepository = cCostCenterRepository;
        this.cCostCenterMapper = cCostCenterMapper;
    }

    /**
     * Save a cCostCenter.
     *
     * @param cCostCenterDTO the entity to save.
     * @return the persisted entity.
     */
    public CCostCenterDTO save(CCostCenterDTO cCostCenterDTO) {
        log.debug("Request to save CCostCenter : {}", cCostCenterDTO);
        CCostCenter cCostCenter = cCostCenterMapper.toEntity(cCostCenterDTO);
        cCostCenter = cCostCenterRepository.save(cCostCenter);
        return cCostCenterMapper.toDto(cCostCenter);
    }

    /**
     * Get all the cCostCenters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CCostCenterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CCostCenters");
        return cCostCenterRepository.findAll(pageable)
            .map(cCostCenterMapper::toDto);
    }

    /**
     * Get one cCostCenter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CCostCenterDTO> findOne(Long id) {
        log.debug("Request to get CCostCenter : {}", id);
        return cCostCenterRepository.findById(id)
            .map(cCostCenterMapper::toDto);
    }

    /**
     * Delete the cCostCenter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CCostCenter : {}", id);
        cCostCenterRepository.deleteById(id);
    }
}
