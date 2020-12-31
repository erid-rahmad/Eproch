package com.bhp.opusb.service;

import com.bhp.opusb.domain.CGalleryItem;
import com.bhp.opusb.repository.CGalleryItemRepository;
import com.bhp.opusb.service.dto.CGalleryItemDTO;
import com.bhp.opusb.service.mapper.CGalleryItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CGalleryItem}.
 */
@Service
@Transactional
public class CGalleryItemService {

    private final Logger log = LoggerFactory.getLogger(CGalleryItemService.class);

    private final CGalleryItemRepository cGalleryItemRepository;

    private final CGalleryItemMapper cGalleryItemMapper;

    public CGalleryItemService(CGalleryItemRepository cGalleryItemRepository, CGalleryItemMapper cGalleryItemMapper) {
        this.cGalleryItemRepository = cGalleryItemRepository;
        this.cGalleryItemMapper = cGalleryItemMapper;
    }

    /**
     * Save a cGalleryItem.
     *
     * @param cGalleryItemDTO the entity to save.
     * @return the persisted entity.
     */
    public CGalleryItemDTO save(CGalleryItemDTO cGalleryItemDTO) {
        log.debug("Request to save CGalleryItem : {}", cGalleryItemDTO);
        CGalleryItem cGalleryItem = cGalleryItemMapper.toEntity(cGalleryItemDTO);
        cGalleryItem = cGalleryItemRepository.save(cGalleryItem);
        return cGalleryItemMapper.toDto(cGalleryItem);
    }

    /**
     * Get all the cGalleryItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CGalleryItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CGalleryItems");
        return cGalleryItemRepository.findAll(pageable)
            .map(cGalleryItemMapper::toDto);
    }


    /**
     * Get one cGalleryItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CGalleryItemDTO> findOne(Long id) {
        log.debug("Request to get CGalleryItem : {}", id);
        return cGalleryItemRepository.findById(id)
            .map(cGalleryItemMapper::toDto);
    }

    /**
     * Delete the cGalleryItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CGalleryItem : {}", id);
        cGalleryItemRepository.deleteById(id);
    }
}
