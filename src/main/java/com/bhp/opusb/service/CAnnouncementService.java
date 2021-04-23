package com.bhp.opusb.service;

import com.bhp.opusb.domain.CAnnouncement;
import com.bhp.opusb.repository.CAnnouncementRepository;
import com.bhp.opusb.service.dto.CAnnouncementDTO;
import com.bhp.opusb.service.mapper.CAnnouncementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CAnnouncement}.
 */
@Service
@Transactional
public class CAnnouncementService {

    private final Logger log = LoggerFactory.getLogger(CAnnouncementService.class);

    private final CAnnouncementRepository cAnnouncementRepository;

    private final CAnnouncementMapper cAnnouncementMapper;

    public CAnnouncementService(CAnnouncementRepository cAnnouncementRepository, CAnnouncementMapper cAnnouncementMapper) {
        this.cAnnouncementRepository = cAnnouncementRepository;
        this.cAnnouncementMapper = cAnnouncementMapper;
    }

    /**
     * Save a cAnnouncement.
     *
     * @param cAnnouncementDTO the entity to save.
     * @return the persisted entity.
     */
    public CAnnouncementDTO save(CAnnouncementDTO cAnnouncementDTO) {
        log.debug("Request to save CAnnouncement : {}", cAnnouncementDTO);
        CAnnouncement cAnnouncement = cAnnouncementMapper.toEntity(cAnnouncementDTO);
        cAnnouncement = cAnnouncementRepository.save(cAnnouncement);
        return cAnnouncementMapper.toDto(cAnnouncement);
    }

    /**
     * Get all the cAnnouncements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CAnnouncementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CAnnouncements");
        return cAnnouncementRepository.findAll(pageable)
            .map(cAnnouncementMapper::toDto);
    }

    /**
     * Get one cAnnouncement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CAnnouncementDTO> findOne(Long id) {
        log.debug("Request to get CAnnouncement : {}", id);
        return cAnnouncementRepository.findById(id)
            .map(cAnnouncementMapper::toDto);
    }

    /**
     * Delete the cAnnouncement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CAnnouncement : {}", id);
        cAnnouncementRepository.deleteById(id);
    }
}
