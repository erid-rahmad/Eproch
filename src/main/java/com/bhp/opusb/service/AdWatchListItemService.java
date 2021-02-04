package com.bhp.opusb.service;

import java.util.Optional;

import com.bhp.opusb.domain.AdWatchListItem;
import com.bhp.opusb.repository.AdWatchListItemRepository;
import com.bhp.opusb.service.dto.AdWatchListItemDTO;
import com.bhp.opusb.service.mapper.AdWatchListItemMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdWatchListItem}.
 */
@Service
@Transactional
public class AdWatchListItemService {

    private final Logger log = LoggerFactory.getLogger(AdWatchListItemService.class);

    private final AdWatchListItemRepository adWatchListItemRepository;

    private final AdWatchListItemMapper adWatchListItemMapper;

    public AdWatchListItemService(AdWatchListItemRepository adWatchListItemRepository, AdWatchListItemMapper adWatchListItemMapper) {
        this.adWatchListItemRepository = adWatchListItemRepository;
        this.adWatchListItemMapper = adWatchListItemMapper;
    }

    /**
     * Save a adWatchListItem.
     *
     * @param adWatchListItemDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.AdWatchList.adWatchListItems", allEntries = true)
    public AdWatchListItemDTO save(AdWatchListItemDTO adWatchListItemDTO) {
        log.debug("Request to save AdWatchListItem : {}", adWatchListItemDTO);
        AdWatchListItem adWatchListItem = adWatchListItemMapper.toEntity(adWatchListItemDTO);
        adWatchListItem = adWatchListItemRepository.save(adWatchListItem);
        return adWatchListItemMapper.toDto(adWatchListItem);
    }

    /**
     * Get all the adWatchListItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdWatchListItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdWatchListItems");
        return adWatchListItemRepository.findAll(pageable)
            .map(adWatchListItemMapper::toDto);
    }

    /**
     * Get one adWatchListItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdWatchListItemDTO> findOne(Long id) {
        log.debug("Request to get AdWatchListItem : {}", id);
        return adWatchListItemRepository.findById(id)
            .map(adWatchListItemMapper::toDto);
    }

    /**
     * Delete the adWatchListItem by id.
     *
     * @param id the id of the entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.AdWatchList.adWatchListItems", allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete AdWatchListItem : {}", id);
        adWatchListItemRepository.deleteById(id);
    }
}
