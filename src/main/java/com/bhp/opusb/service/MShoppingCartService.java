package com.bhp.opusb.service;

import com.bhp.opusb.domain.MShoppingCart;
import com.bhp.opusb.repository.MShoppingCartRepository;
import com.bhp.opusb.service.dto.MShoppingCartDTO;
import com.bhp.opusb.service.mapper.MShoppingCartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MShoppingCart}.
 */
@Service
@Transactional
public class MShoppingCartService {

    private final Logger log = LoggerFactory.getLogger(MShoppingCartService.class);

    private final MShoppingCartRepository mShoppingCartRepository;

    private final MShoppingCartMapper mShoppingCartMapper;

    public MShoppingCartService(MShoppingCartRepository mShoppingCartRepository, MShoppingCartMapper mShoppingCartMapper) {
        this.mShoppingCartRepository = mShoppingCartRepository;
        this.mShoppingCartMapper = mShoppingCartMapper;
    }

    /**
     * Save a mShoppingCart.
     *
     * @param mShoppingCartDTO the entity to save.
     * @return the persisted entity.
     */
    public MShoppingCartDTO save(MShoppingCartDTO mShoppingCartDTO) {
        log.debug("Request to save MShoppingCart : {}", mShoppingCartDTO);
        MShoppingCart mShoppingCart = mShoppingCartMapper.toEntity(mShoppingCartDTO);
        mShoppingCart = mShoppingCartRepository.save(mShoppingCart);
        return mShoppingCartMapper.toDto(mShoppingCart);
    }

    /**
     * Get all the mShoppingCarts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MShoppingCartDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MShoppingCarts");
        return mShoppingCartRepository.findAll(pageable)
            .map(mShoppingCartMapper::toDto);
    }


    /**
     * Get one mShoppingCart by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MShoppingCartDTO> findOne(Long id) {
        log.debug("Request to get MShoppingCart : {}", id);
        return mShoppingCartRepository.findById(id)
            .map(mShoppingCartMapper::toDto);
    }

    /**
     * Delete the mShoppingCart by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MShoppingCart : {}", id);
        mShoppingCartRepository.deleteById(id);
    }
}
