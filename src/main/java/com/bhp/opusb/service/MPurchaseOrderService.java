package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPurchaseOrder;
import com.bhp.opusb.repository.MPurchaseOrderRepository;
import com.bhp.opusb.service.dto.MPurchaseOrderDTO;
import com.bhp.opusb.service.mapper.MPurchaseOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MPurchaseOrder}.
 */
@Service
@Transactional
public class MPurchaseOrderService {

    private final Logger log = LoggerFactory.getLogger(MPurchaseOrderService.class);

    private final MPurchaseOrderRepository mPurchaseOrderRepository;

    private final MPurchaseOrderMapper mPurchaseOrderMapper;

    public MPurchaseOrderService(MPurchaseOrderRepository mPurchaseOrderRepository, MPurchaseOrderMapper mPurchaseOrderMapper) {
        this.mPurchaseOrderRepository = mPurchaseOrderRepository;
        this.mPurchaseOrderMapper = mPurchaseOrderMapper;
    }

    /**
     * Save a mPurchaseOrder.
     *
     * @param mPurchaseOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public MPurchaseOrderDTO save(MPurchaseOrderDTO mPurchaseOrderDTO) {
        log.debug("Request to save MPurchaseOrder : {}", mPurchaseOrderDTO);
        MPurchaseOrder mPurchaseOrder = mPurchaseOrderMapper.toEntity(mPurchaseOrderDTO);
        mPurchaseOrder = mPurchaseOrderRepository.save(mPurchaseOrder);
        return mPurchaseOrderMapper.toDto(mPurchaseOrder);
    }

    /**
     * Get all the mPurchaseOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPurchaseOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPurchaseOrders");
        return mPurchaseOrderRepository.findAll(pageable)
            .map(mPurchaseOrderMapper::toDto);
    }

    /**
     * Get one mPurchaseOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPurchaseOrderDTO> findOne(Long id) {
        log.debug("Request to get MPurchaseOrder : {}", id);
        return mPurchaseOrderRepository.findById(id)
            .map(mPurchaseOrderMapper::toDto);
    }

    /**
     * Delete the mPurchaseOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPurchaseOrder : {}", id);
        mPurchaseOrderRepository.deleteById(id);
    }
}
