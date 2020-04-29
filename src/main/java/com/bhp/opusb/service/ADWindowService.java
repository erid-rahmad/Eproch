package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADWindow;
import com.bhp.opusb.repository.ADWindowRepository;
import com.bhp.opusb.service.dto.ADWindowDTO;
import com.bhp.opusb.service.mapper.ADWindowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ADWindow}.
 */
@Service
@Transactional
public class ADWindowService {

    private final Logger log = LoggerFactory.getLogger(ADWindowService.class);

    private final ADWindowRepository aDWindowRepository;

    private final ADWindowMapper aDWindowMapper;

    public ADWindowService(ADWindowRepository aDWindowRepository, ADWindowMapper aDWindowMapper) {
        this.aDWindowRepository = aDWindowRepository;
        this.aDWindowMapper = aDWindowMapper;
    }

    /**
     * Save a aDWindow.
     *
     * @param aDWindowDTO the entity to save.
     * @return the persisted entity.
     */
    public ADWindowDTO save(ADWindowDTO aDWindowDTO) {
        log.debug("Request to save ADWindow : {}", aDWindowDTO);
        ADWindow aDWindow = aDWindowMapper.toEntity(aDWindowDTO);
        aDWindow = aDWindowRepository.save(aDWindow);
        return aDWindowMapper.toDto(aDWindow);
    }

    /**
     * Get all the aDWindows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADWindowDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADWindows");
        return aDWindowRepository.findAll(pageable)
            .map(aDWindowMapper::toDto);
    }

    /**
     * Get one aDWindow by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADWindowDTO> findOne(Long id) {
        log.debug("Request to get ADWindow : {}", id);
        return aDWindowRepository.findById(id)
            .map(aDWindowMapper::toDto);
    }

    /**
     * Delete the aDWindow by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ADWindow : {}", id);
        aDWindowRepository.deleteById(id);
    }
}
