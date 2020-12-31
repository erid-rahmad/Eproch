package com.bhp.opusb.service;

import com.bhp.opusb.domain.MShoppingCartItem;
import com.bhp.opusb.repository.MShoppingCartItemRepository;
import com.bhp.opusb.service.dto.MShoppingCartItemDTO;
import com.bhp.opusb.service.mapper.MShoppingCartItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MShoppingCartItem}.
 */
@Service
@Transactional
public class MShoppingCartItemService {

    private final Logger log = LoggerFactory.getLogger(MShoppingCartItemService.class);

    private final MShoppingCartItemRepository mShoppingCartItemRepository;

    private final MShoppingCartItemMapper mShoppingCartItemMapper;

    public MShoppingCartItemService(MShoppingCartItemRepository mShoppingCartItemRepository, MShoppingCartItemMapper mShoppingCartItemMapper) {
        this.mShoppingCartItemRepository = mShoppingCartItemRepository;
        this.mShoppingCartItemMapper = mShoppingCartItemMapper;
    }

    /**
     * Save a mShoppingCartItem.
     *
     * @param mShoppingCartItemDTO the entity to save.
     * @return the persisted entity.
     */
    public MShoppingCartItemDTO save(MShoppingCartItemDTO mShoppingCartItemDTO) {
        log.debug("Request to save MShoppingCartItem : {}", mShoppingCartItemDTO);
        MShoppingCartItem mShoppingCartItem = mShoppingCartItemMapper.toEntity(mShoppingCartItemDTO);
        mShoppingCartItem = mShoppingCartItemRepository.save(mShoppingCartItem);
        return mShoppingCartItemMapper.toDto(mShoppingCartItem);
    }

    /**
     * Get all the mShoppingCartItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MShoppingCartItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MShoppingCartItems");
        return mShoppingCartItemRepository.findAll(pageable)
            .map(mShoppingCartItemMapper::toDto);
    }


    /**
     * Get one mShoppingCartItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MShoppingCartItemDTO> findOne(Long id) {
        log.debug("Request to get MShoppingCartItem : {}", id);
        return mShoppingCartItemRepository.findById(id)
            .map(mShoppingCartItemMapper::toDto);
    }

    /**
     * Delete the mShoppingCartItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MShoppingCartItem : {}", id);
        mShoppingCartItemRepository.deleteById(id);
    }
}
