package com.bhp.opusb.service;

import com.bhp.opusb.domain.MProposalPriceSubItem;
import com.bhp.opusb.repository.MProposalPriceSubItemRepository;
import com.bhp.opusb.service.dto.MProposalPriceSubItemDTO;
import com.bhp.opusb.service.mapper.MProposalPriceSubItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MProposalPriceSubItem}.
 */
@Service
@Transactional
public class MProposalPriceSubItemService {

    private final Logger log = LoggerFactory.getLogger(MProposalPriceSubItemService.class);

    private final MProposalPriceSubItemRepository mProposalPriceSubItemRepository;

    private final MProposalPriceSubItemMapper mProposalPriceSubItemMapper;

    public MProposalPriceSubItemService(MProposalPriceSubItemRepository mProposalPriceSubItemRepository, MProposalPriceSubItemMapper mProposalPriceSubItemMapper) {
        this.mProposalPriceSubItemRepository = mProposalPriceSubItemRepository;
        this.mProposalPriceSubItemMapper = mProposalPriceSubItemMapper;
    }

    /**
     * Save a mProposalPriceSubItem.
     *
     * @param mProposalPriceSubItemDTO the entity to save.
     * @return the persisted entity.
     */
    public MProposalPriceSubItemDTO save(MProposalPriceSubItemDTO mProposalPriceSubItemDTO) {
        log.debug("Request to save MProposalPriceSubItem : {}", mProposalPriceSubItemDTO);
        MProposalPriceSubItem mProposalPriceSubItem = mProposalPriceSubItemMapper.toEntity(mProposalPriceSubItemDTO);
        mProposalPriceSubItem = mProposalPriceSubItemRepository.save(mProposalPriceSubItem);
        return mProposalPriceSubItemMapper.toDto(mProposalPriceSubItem);
    }

    /**
     * Get all the mProposalPriceSubItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalPriceSubItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProposalPriceSubItems");
        return mProposalPriceSubItemRepository.findAll(pageable)
            .map(mProposalPriceSubItemMapper::toDto);
    }

    /**
     * Get one mProposalPriceSubItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProposalPriceSubItemDTO> findOne(Long id) {
        log.debug("Request to get MProposalPriceSubItem : {}", id);
        return mProposalPriceSubItemRepository.findById(id)
            .map(mProposalPriceSubItemMapper::toDto);
    }

    /**
     * Delete the mProposalPriceSubItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProposalPriceSubItem : {}", id);
        mProposalPriceSubItemRepository.deleteById(id);
    }
}
