package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdWatchList;
import com.bhp.opusb.repository.AdWatchListRepository;
import com.bhp.opusb.service.dto.AdWatchListDTO;
import com.bhp.opusb.service.mapper.AdWatchListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdWatchList}.
 */
@Service
@Transactional
public class AdWatchListService {

    private final Logger log = LoggerFactory.getLogger(AdWatchListService.class);

    private final AdWatchListRepository adWatchListRepository;

    private final AdWatchListMapper adWatchListMapper;

    public AdWatchListService(AdWatchListRepository adWatchListRepository, AdWatchListMapper adWatchListMapper) {
        this.adWatchListRepository = adWatchListRepository;
        this.adWatchListMapper = adWatchListMapper;
    }

    /**
     * Save a adWatchList.
     *
     * @param adWatchListDTO the entity to save.
     * @return the persisted entity.
     */
    public AdWatchListDTO save(AdWatchListDTO adWatchListDTO) {
        log.debug("Request to save AdWatchList : {}", adWatchListDTO);
        AdWatchList adWatchList = adWatchListMapper.toEntity(adWatchListDTO);
        adWatchList = adWatchListRepository.save(adWatchList);
        return adWatchListMapper.toDto(adWatchList);
    }

    /**
     * Get all the adWatchLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdWatchListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdWatchLists");
        return adWatchListRepository.findAll(pageable)
            .map(adWatchListMapper::toDto);
    }

    /**
     * Get one adWatchList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdWatchListDTO> findOne(Long id) {
        log.debug("Request to get AdWatchList : {}", id);
        return adWatchListRepository.findById(id)
            .map(adWatchListMapper::toDto);
    }

    /**
     * Delete the adWatchList by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdWatchList : {}", id);
        adWatchListRepository.deleteById(id);
    }
}
