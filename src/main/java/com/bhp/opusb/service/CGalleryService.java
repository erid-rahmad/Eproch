package com.bhp.opusb.service;

import com.bhp.opusb.domain.CGallery;
import com.bhp.opusb.repository.CGalleryRepository;
import com.bhp.opusb.service.dto.CGalleryDTO;
import com.bhp.opusb.service.mapper.CGalleryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CGallery}.
 */
@Service
@Transactional
public class CGalleryService {

    private final Logger log = LoggerFactory.getLogger(CGalleryService.class);

    private final CGalleryRepository cGalleryRepository;

    private final CGalleryMapper cGalleryMapper;

    public CGalleryService(CGalleryRepository cGalleryRepository, CGalleryMapper cGalleryMapper) {
        this.cGalleryRepository = cGalleryRepository;
        this.cGalleryMapper = cGalleryMapper;
    }

    /**
     * Save a cGallery.
     *
     * @param cGalleryDTO the entity to save.
     * @return the persisted entity.
     */
    public CGalleryDTO save(CGalleryDTO cGalleryDTO) {
        log.debug("Request to save CGallery : {}", cGalleryDTO);
        CGallery cGallery = cGalleryMapper.toEntity(cGalleryDTO);
        cGallery = cGalleryRepository.save(cGallery);
        return cGalleryMapper.toDto(cGallery);
    }

    /**
     * Get all the cGalleries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CGalleryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CGalleries");
        return cGalleryRepository.findAll(pageable)
            .map(cGalleryMapper::toDto);
    }


    /**
     * Get one cGallery by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CGalleryDTO> findOne(Long id) {
        log.debug("Request to get CGallery : {}", id);
        return cGalleryRepository.findById(id)
            .map(cGalleryMapper::toDto);
    }

    /**
     * Delete the cGallery by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CGallery : {}", id);
        cGalleryRepository.deleteById(id);
    }
}
