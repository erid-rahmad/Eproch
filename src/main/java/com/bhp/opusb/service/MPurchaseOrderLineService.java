package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPurchaseOrderLine;
import com.bhp.opusb.domain.MRequisitionLine;
import com.bhp.opusb.repository.MPurchaseOrderLineRepository;
import com.bhp.opusb.service.dto.MPurchaseOrderLineDTO;
import com.bhp.opusb.service.mapper.MPurchaseOrderLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MPurchaseOrderLine}.
 */
@Service
@Transactional
public class MPurchaseOrderLineService {

    private final Logger log = LoggerFactory.getLogger(MPurchaseOrderLineService.class);

    private final MPurchaseOrderLineRepository mPurchaseOrderLineRepository;

    private final MPurchaseOrderLineMapper mPurchaseOrderLineMapper;

    public MPurchaseOrderLineService(MPurchaseOrderLineRepository mPurchaseOrderLineRepository, MPurchaseOrderLineMapper mPurchaseOrderLineMapper) {
        this.mPurchaseOrderLineRepository = mPurchaseOrderLineRepository;
        this.mPurchaseOrderLineMapper = mPurchaseOrderLineMapper;
    }

    /**
     * Save a mPurchaseOrderLine.
     *
     * @param mPurchaseOrderLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MPurchaseOrderLineDTO save(MPurchaseOrderLineDTO mPurchaseOrderLineDTO) {
        log.debug("Request to save MPurchaseOrderLine : {}", mPurchaseOrderLineDTO);
        MPurchaseOrderLine mPurchaseOrderLine = mPurchaseOrderLineMapper.toEntity(mPurchaseOrderLineDTO);
        mPurchaseOrderLine = mPurchaseOrderLineRepository.save(mPurchaseOrderLine);
        return mPurchaseOrderLineMapper.toDto(mPurchaseOrderLine);
    }

    /**
     * Get all the mPurchaseOrderLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPurchaseOrderLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPurchaseOrderLines");
        return mPurchaseOrderLineRepository.findAll(pageable)
            .map(mPurchaseOrderLineMapper::toDto);
    }

    /**
     * Get one mPurchaseOrderLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPurchaseOrderLineDTO> findOne(Long id) {
        log.debug("Request to get MPurchaseOrderLine : {}", id);
        return mPurchaseOrderLineRepository.findById(id)
            .map(mPurchaseOrderLineMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<MPurchaseOrderLine> mPOLineList(long id){
        List<MPurchaseOrderLine> mRequisitionLines = mPurchaseOrderLineRepository.mPOlinebyidpr(id);
        return mRequisitionLines;
    }

    /**
     * Delete the mPurchaseOrderLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPurchaseOrderLine : {}", id);
        mPurchaseOrderLineRepository.deleteById(id);
    }
}
