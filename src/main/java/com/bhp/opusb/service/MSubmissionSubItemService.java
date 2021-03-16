package com.bhp.opusb.service;

import com.bhp.opusb.domain.MSubmissionSubItem;
import com.bhp.opusb.repository.MSubmissionSubItemRepository;
import com.bhp.opusb.service.dto.MSubmissionSubItemDTO;
import com.bhp.opusb.service.mapper.MSubmissionSubItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MSubmissionSubItem}.
 */
@Service
@Transactional
public class MSubmissionSubItemService {

    private final Logger log = LoggerFactory.getLogger(MSubmissionSubItemService.class);

    private final MSubmissionSubItemRepository mSubmissionSubItemRepository;

    private final MSubmissionSubItemMapper mSubmissionSubItemMapper;

    public MSubmissionSubItemService(MSubmissionSubItemRepository mSubmissionSubItemRepository, MSubmissionSubItemMapper mSubmissionSubItemMapper) {
        this.mSubmissionSubItemRepository = mSubmissionSubItemRepository;
        this.mSubmissionSubItemMapper = mSubmissionSubItemMapper;
    }

    /**
     * Save a mSubmissionSubItem.
     *
     * @param mSubmissionSubItemDTO the entity to save.
     * @return the persisted entity.
     */
    public MSubmissionSubItemDTO save(MSubmissionSubItemDTO mSubmissionSubItemDTO) {
        log.debug("Request to save MSubmissionSubItem : {}", mSubmissionSubItemDTO);
        MSubmissionSubItem mSubmissionSubItem = mSubmissionSubItemMapper.toEntity(mSubmissionSubItemDTO);
        mSubmissionSubItem = mSubmissionSubItemRepository.save(mSubmissionSubItem);
        return mSubmissionSubItemMapper.toDto(mSubmissionSubItem);
    }

    /**
     * Get all the mSubmissionSubItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MSubmissionSubItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MSubmissionSubItems");
        return mSubmissionSubItemRepository.findAll(pageable)
            .map(mSubmissionSubItemMapper::toDto);
    }


    /**
     * Get one mSubmissionSubItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MSubmissionSubItemDTO> findOne(Long id) {
        log.debug("Request to get MSubmissionSubItem : {}", id);
        return mSubmissionSubItemRepository.findById(id)
            .map(mSubmissionSubItemMapper::toDto);
    }

    /**
     * Delete the mSubmissionSubItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MSubmissionSubItem : {}", id);
        mSubmissionSubItemRepository.deleteById(id);
    }
}
