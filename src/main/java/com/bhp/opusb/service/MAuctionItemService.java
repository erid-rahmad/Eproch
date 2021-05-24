package com.bhp.opusb.service;

import com.bhp.opusb.domain.MAuctionItem;
import com.bhp.opusb.repository.MAuctionItemRepository;
import com.bhp.opusb.service.dto.MAuctionItemDTO;
import com.bhp.opusb.service.mapper.MAuctionItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MAuctionItem}.
 */
@Service
@Transactional
public class MAuctionItemService {

    private final Logger log = LoggerFactory.getLogger(MAuctionItemService.class);

    private final MAuctionItemRepository mAuctionItemRepository;

    private final MAuctionItemMapper mAuctionItemMapper;

    public MAuctionItemService(MAuctionItemRepository mAuctionItemRepository, MAuctionItemMapper mAuctionItemMapper) {
        this.mAuctionItemRepository = mAuctionItemRepository;
        this.mAuctionItemMapper = mAuctionItemMapper;
    }

    /**
     * Save a mAuctionItem.
     *
     * @param mAuctionItemDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionItemDTO save(MAuctionItemDTO mAuctionItemDTO) {
        log.debug("Request to save MAuctionItem : {}", mAuctionItemDTO);
        MAuctionItem mAuctionItem = mAuctionItemMapper.toEntity(mAuctionItemDTO);
        mAuctionItem = mAuctionItemRepository.save(mAuctionItem);
        return mAuctionItemMapper.toDto(mAuctionItem);
    }

    /**
     * Get all the mAuctionItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctionItems");
        return mAuctionItemRepository.findAll(pageable)
            .map(mAuctionItemMapper::toDto);
    }

    /**
     * Get one mAuctionItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionItemDTO> findOne(Long id) {
        log.debug("Request to get MAuctionItem : {}", id);
        return mAuctionItemRepository.findById(id)
            .map(mAuctionItemMapper::toDto);
    }

    /**
     * Delete the mAuctionItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuctionItem : {}", id);
        mAuctionItemRepository.deleteById(id);
    }
}
