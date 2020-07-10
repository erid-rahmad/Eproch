package com.bhp.opusb.service;

import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.CAttachmentRepository;
import com.bhp.opusb.service.dto.CAttachmentDTO;
import com.bhp.opusb.service.mapper.CAttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CAttachment}.
 */
@Service
@Transactional
public class CAttachmentService {

    private final Logger log = LoggerFactory.getLogger(CAttachmentService.class);

    private final CAttachmentRepository cAttachmentRepository;

    private final CAttachmentMapper cAttachmentMapper;

    public CAttachmentService(CAttachmentRepository cAttachmentRepository, CAttachmentMapper cAttachmentMapper) {
        this.cAttachmentRepository = cAttachmentRepository;
        this.cAttachmentMapper = cAttachmentMapper;
    }

    /**
     * Save a cAttachment.
     *
     * @param cAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    public CAttachmentDTO save(CAttachmentDTO cAttachmentDTO) {
        log.debug("Request to save CAttachment : {}", cAttachmentDTO);
        CAttachment cAttachment = cAttachmentMapper.toEntity(cAttachmentDTO);
        cAttachment = cAttachmentRepository.save(cAttachment);
        return cAttachmentMapper.toDto(cAttachment);
    }

    /**
     * Get all the cAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CAttachments");
        return cAttachmentRepository.findAll(pageable)
            .map(cAttachmentMapper::toDto);
    }

    /**
     * Get one cAttachment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CAttachmentDTO> findOne(Long id) {
        log.debug("Request to get CAttachment : {}", id);
        return cAttachmentRepository.findById(id)
            .map(cAttachmentMapper::toDto);
    }

    /**
     * Delete the cAttachment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CAttachment : {}", id);
        cAttachmentRepository.deleteById(id);
    }
}
