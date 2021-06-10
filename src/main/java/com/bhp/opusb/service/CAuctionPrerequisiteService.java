package com.bhp.opusb.service;

import com.bhp.opusb.domain.CAuctionPrerequisite;
import com.bhp.opusb.repository.CAuctionPrerequisiteRepository;
import com.bhp.opusb.service.dto.CAuctionPrerequisiteDTO;
import com.bhp.opusb.service.mapper.CAuctionPrerequisiteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CAuctionPrerequisite}.
 */
@Service
@Transactional
public class CAuctionPrerequisiteService {

    private final Logger log = LoggerFactory.getLogger(CAuctionPrerequisiteService.class);

    private final CAuctionPrerequisiteRepository cAuctionPrerequisiteRepository;

    private final CAuctionPrerequisiteMapper cAuctionPrerequisiteMapper;

    public CAuctionPrerequisiteService(CAuctionPrerequisiteRepository cAuctionPrerequisiteRepository, CAuctionPrerequisiteMapper cAuctionPrerequisiteMapper) {
        this.cAuctionPrerequisiteRepository = cAuctionPrerequisiteRepository;
        this.cAuctionPrerequisiteMapper = cAuctionPrerequisiteMapper;
    }

    /**
     * Save a cAuctionPrerequisite.
     *
     * @param cAuctionPrerequisiteDTO the entity to save.
     * @return the persisted entity.
     */
    public CAuctionPrerequisiteDTO save(CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO) {
        log.debug("Request to save CAuctionPrerequisite : {}", cAuctionPrerequisiteDTO);
        CAuctionPrerequisite cAuctionPrerequisite = cAuctionPrerequisiteMapper.toEntity(cAuctionPrerequisiteDTO);
        cAuctionPrerequisite = cAuctionPrerequisiteRepository.save(cAuctionPrerequisite);
        return cAuctionPrerequisiteMapper.toDto(cAuctionPrerequisite);
    }

    /**
     * Get all the cAuctionPrerequisites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CAuctionPrerequisiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CAuctionPrerequisites");
        return cAuctionPrerequisiteRepository.findAll(pageable)
            .map(cAuctionPrerequisiteMapper::toDto);
    }

    /**
     * Get one cAuctionPrerequisite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CAuctionPrerequisiteDTO> findOne(Long id) {
        log.debug("Request to get CAuctionPrerequisite : {}", id);
        return cAuctionPrerequisiteRepository.findById(id)
            .map(cAuctionPrerequisiteMapper::toDto);
    }

    /**
     * Delete the cAuctionPrerequisite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CAuctionPrerequisite : {}", id);
        cAuctionPrerequisiteRepository.deleteById(id);
    }
}
