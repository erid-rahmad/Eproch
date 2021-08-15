package com.bhp.opusb.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.MAuctionSubmissionItem;
import com.bhp.opusb.repository.MAuctionSubmissionItemRepository;
import com.bhp.opusb.service.dto.MAuctionSubmissionDTO;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemCriteria;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemDTO;
import com.bhp.opusb.service.mapper.MAuctionSubmissionItemMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.filter.LongFilter;

/**
 * Service Implementation for managing {@link MAuctionSubmissionItem}.
 */
@Service
@Transactional
public class MAuctionSubmissionItemService {

    private final Logger log = LoggerFactory.getLogger(MAuctionSubmissionItemService.class);

    private final MAuctionSubmissionItemQueryService mAuctionSubmissionItemQueryService;

    private final MAuctionSubmissionItemRepository mAuctionSubmissionItemRepository;

    private final MAuctionSubmissionItemMapper mAuctionSubmissionItemMapper;

    public MAuctionSubmissionItemService(MAuctionSubmissionItemQueryService mAuctionSubmissionItemQueryService,
            MAuctionSubmissionItemRepository mAuctionSubmissionItemRepository,
            MAuctionSubmissionItemMapper mAuctionSubmissionItemMapper) {
        this.mAuctionSubmissionItemQueryService = mAuctionSubmissionItemQueryService;
        this.mAuctionSubmissionItemRepository = mAuctionSubmissionItemRepository;
        this.mAuctionSubmissionItemMapper = mAuctionSubmissionItemMapper;
    }

    /**
     * Save a mAuctionSubmissionItem.
     *
     * @param mAuctionSubmissionItemDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionSubmissionItemDTO save(MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO) {
        log.debug("Request to save MAuctionSubmissionItem : {}", mAuctionSubmissionItemDTO);
        MAuctionSubmissionItem mAuctionSubmissionItem = mAuctionSubmissionItemMapper.toEntity(mAuctionSubmissionItemDTO);
        mAuctionSubmissionItem = mAuctionSubmissionItemRepository.save(mAuctionSubmissionItem);
        return mAuctionSubmissionItemMapper.toDto(mAuctionSubmissionItem);
    }

    public Optional<MAuctionSubmissionItemDTO> submitBid(Long auctionId, Long vendorId, Long itemId, BigDecimal price) {
        MAuctionSubmissionItemCriteria criteria = new MAuctionSubmissionItemCriteria();
        LongFilter auctionIdFilter = new LongFilter();
        LongFilter vendorIdFilter = new LongFilter();
        LongFilter itemIdFilter = new LongFilter();
        MAuctionSubmissionItemDTO submissionItemDTO = null;
        
        auctionIdFilter.setEquals(auctionId);
        vendorIdFilter.setEquals(vendorId);
        itemIdFilter.setEquals(itemId);
        criteria.setAuctionId(auctionIdFilter);
        criteria.setVendorId(vendorIdFilter);
        criteria.setAuctionItemId(itemIdFilter);
        List<MAuctionSubmissionItemDTO> items = mAuctionSubmissionItemQueryService.findByCriteria(criteria);

        if ( ! items.isEmpty()) {
            MAuctionSubmissionItemDTO submittedItem = items.get(0);
            submittedItem.setPrice(price);
            submissionItemDTO = save(submittedItem);
        }

        return Optional.ofNullable(submissionItemDTO);
    }

    /**
     * Save a list of mAuctionSubmissionItems.
     *
     * @param mAuctionSubmissionItemDTOs the entities to save.
     * @return the persisted entities.
     */
    public List<MAuctionSubmissionItemDTO> saveAll(List<MAuctionSubmissionItemDTO> mAuctionSubmissionItemDTOs, MAuctionSubmissionDTO mAuctionSubmissionDTO) {
        log.debug("Request to save MAuctionSubmissionItems. count : {}", mAuctionSubmissionItemDTOs.size());
        mAuctionSubmissionItemDTOs.forEach(item -> item.setAuctionSubmissionId(mAuctionSubmissionDTO.getId()));
        List<MAuctionSubmissionItem> mAuctionSubmissionItem = mAuctionSubmissionItemMapper.toEntity(mAuctionSubmissionItemDTOs);
        mAuctionSubmissionItem = mAuctionSubmissionItemRepository.saveAll(mAuctionSubmissionItem);

        // Delete uninsterested items from the existing submission.
        final List<Long> itemIds = mAuctionSubmissionItemDTOs.stream()
            .map(MAuctionSubmissionItemDTO::getAuctionItemId)
            .collect(Collectors.toList());

        mAuctionSubmissionItemRepository.deleteByAuctionItemIdNotIn(itemIds, mAuctionSubmissionDTO.getId());
        
        return mAuctionSubmissionItemMapper.toDto(mAuctionSubmissionItem);
    }

    /**
     * Get all the mAuctionSubmissionItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionSubmissionItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctionSubmissionItems");
        return mAuctionSubmissionItemRepository.findAll(pageable)
            .map(mAuctionSubmissionItemMapper::toDto);
    }

    /**
     * Get one mAuctionSubmissionItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionSubmissionItemDTO> findOne(Long id) {
        log.debug("Request to get MAuctionSubmissionItem : {}", id);
        return mAuctionSubmissionItemRepository.findById(id)
            .map(mAuctionSubmissionItemMapper::toDto);
    }

    /**
     * Delete the mAuctionSubmissionItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuctionSubmissionItem : {}", id);
        mAuctionSubmissionItemRepository.deleteById(id);
    }
}
