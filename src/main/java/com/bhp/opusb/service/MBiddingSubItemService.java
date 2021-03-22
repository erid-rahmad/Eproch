package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingSubItem;
import com.bhp.opusb.repository.MBiddingSubItemRepository;
import com.bhp.opusb.service.dto.MBiddingSubItemDTO;
import com.bhp.opusb.service.dto.MBiddingSubItemLineDTO;
import com.bhp.opusb.service.mapper.MBiddingSubItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link MBiddingSubItem}.
 */
@Service
@Transactional
public class MBiddingSubItemService {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubItemService.class);

    private final MBiddingSubItemRepository mBiddingSubItemRepository;
    private final MBiddingSubItemLineService mBiddingSubItemLineService;

    private final MBiddingSubItemMapper mBiddingSubItemMapper;

    public MBiddingSubItemService(MBiddingSubItemRepository mBiddingSubItemRepository,
            MBiddingSubItemLineService mBiddingSubItemLineService, MBiddingSubItemMapper mBiddingSubItemMapper) {
        this.mBiddingSubItemLineService = mBiddingSubItemLineService;
        this.mBiddingSubItemRepository = mBiddingSubItemRepository;
        this.mBiddingSubItemMapper = mBiddingSubItemMapper;
    }

    /**
     * Save a mBiddingSubItem.
     *
     * @param mBiddingSubItemDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingSubItemDTO save(MBiddingSubItemDTO mBiddingSubItemDTO) {
        log.debug("Request to save MBiddingSubItem : {}", mBiddingSubItemDTO);
        final MBiddingSubItem mBiddingSubItem = mBiddingSubItemMapper.toEntity(mBiddingSubItemDTO);
        mBiddingSubItemRepository.save(mBiddingSubItem);

        // Set sub-item ID for each sub-item line.
        mBiddingSubItemDTO.getmBiddingSubItemLines().stream()
            .forEach(line -> line.setBiddingSubItemId(mBiddingSubItem.getId()));

        // Batch save all sub-item lines.
        mBiddingSubItemLineService.saveAll(mBiddingSubItemDTO.getmBiddingSubItemLines());
        return mBiddingSubItemMapper.toDto(mBiddingSubItem);
    }

    /**
     * Save all mBiddingSubItems.
     *
     * @param mBiddingSubItemDTOs the list of entities to save.
     * @return the persisted entities.
     */
    public List<MBiddingSubItemDTO> saveAll(List<MBiddingSubItemDTO> mBiddingSubItemDTOs) {
        log.debug("Request to save list of MBiddingSubItems. size : {}", mBiddingSubItemDTOs.size());
        List<MBiddingSubItem> mBiddingSubItems = mBiddingSubItemMapper.toEntity(mBiddingSubItemDTOs);
        mBiddingSubItems = mBiddingSubItemRepository.saveAll(mBiddingSubItems);

        final List<MBiddingSubItemLineDTO> mBiddingSubItemLineDTOs = new ArrayList<>();

        // Set sub-item ID for each sub-item line.
        for (int i = 0; i < mBiddingSubItems.size(); ++i) {
            MBiddingSubItem mBiddingSubItem = mBiddingSubItems.get(i);
            MBiddingSubItemDTO mBiddingSubItemDTO = mBiddingSubItemDTOs.get(i);
            mBiddingSubItemDTO.getmBiddingSubItemLines().stream()
                .forEach(line -> {
                    line.setBiddingSubItemId(mBiddingSubItem.getId());
                    mBiddingSubItemLineDTOs.add(line);
                });
        }

        // Batch save all sub-item lines.
        mBiddingSubItemLineService.saveAll(mBiddingSubItemLineDTOs);
        return mBiddingSubItemMapper.toDto(mBiddingSubItems);
    }

    /**
     * Get all the mBiddingSubItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingSubItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingSubItems");
        return mBiddingSubItemRepository.findAll(pageable)
            .map(mBiddingSubItemMapper::toDto);
    }


    /**
     *  Get all the mBiddingSubItems where BiddingLine is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<MBiddingSubItemDTO> findAllWhereBiddingLineIsNull() {
        log.debug("Request to get all mBiddingSubItems where BiddingLine is null");
        return StreamSupport
            .stream(mBiddingSubItemRepository.findAll().spliterator(), false)
            .filter(mBiddingSubItem -> mBiddingSubItem.getBiddingLine() == null)
            .map(mBiddingSubItemMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mBiddingSubItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingSubItemDTO> findOne(Long id) {
        log.debug("Request to get MBiddingSubItem : {}", id);
        return mBiddingSubItemRepository.findById(id)
            .map(mBiddingSubItemMapper::toDto);
    }

    /**
     * Delete the mBiddingSubItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingSubItem : {}", id);
        mBiddingSubItemRepository.deleteById(id);
    }
}
