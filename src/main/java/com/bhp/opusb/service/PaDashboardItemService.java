package com.bhp.opusb.service;

import com.bhp.opusb.domain.PaDashboardItem;
import com.bhp.opusb.repository.PaDashboardItemRepository;
import com.bhp.opusb.service.dto.PaDashboardItemDTO;
import com.bhp.opusb.service.mapper.PaDashboardItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PaDashboardItem}.
 */
@Service
@Transactional
public class PaDashboardItemService {

    private final Logger log = LoggerFactory.getLogger(PaDashboardItemService.class);

    private final PaDashboardItemRepository paDashboardItemRepository;

    private final PaDashboardItemMapper paDashboardItemMapper;

    public PaDashboardItemService(PaDashboardItemRepository paDashboardItemRepository, PaDashboardItemMapper paDashboardItemMapper) {
        this.paDashboardItemRepository = paDashboardItemRepository;
        this.paDashboardItemMapper = paDashboardItemMapper;
    }

    /**
     * Save a paDashboardItem.
     *
     * @param paDashboardItemDTO the entity to save.
     * @return the persisted entity.
     */
    public PaDashboardItemDTO save(PaDashboardItemDTO paDashboardItemDTO) {
        log.debug("Request to save PaDashboardItem : {}", paDashboardItemDTO);
        PaDashboardItem paDashboardItem = paDashboardItemMapper.toEntity(paDashboardItemDTO);
        paDashboardItem = paDashboardItemRepository.save(paDashboardItem);
        return paDashboardItemMapper.toDto(paDashboardItem);
    }

    /**
     * Get all the paDashboardItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaDashboardItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaDashboardItems");
        return paDashboardItemRepository.findAll(pageable)
            .map(paDashboardItemMapper::toDto);
    }

    /**
     * Get one paDashboardItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaDashboardItemDTO> findOne(Long id) {
        log.debug("Request to get PaDashboardItem : {}", id);
        return paDashboardItemRepository.findById(id)
            .map(paDashboardItemMapper::toDto);
    }

    /**
     * Delete the paDashboardItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaDashboardItem : {}", id);
        paDashboardItemRepository.deleteById(id);
    }
}
