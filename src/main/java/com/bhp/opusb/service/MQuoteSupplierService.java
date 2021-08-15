package com.bhp.opusb.service;

import com.bhp.opusb.domain.MQuoteSupplier;
import com.bhp.opusb.repository.MQuoteSupplierRepository;
import com.bhp.opusb.service.dto.MQuoteSupplierDTO;
import com.bhp.opusb.service.mapper.MQuoteSupplierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MQuoteSupplier}.
 */
@Service
@Transactional
public class MQuoteSupplierService {

    private final Logger log = LoggerFactory.getLogger(MQuoteSupplierService.class);

    private final MQuoteSupplierRepository mQuoteSupplierRepository;

    private final MQuoteSupplierMapper mQuoteSupplierMapper;

    public MQuoteSupplierService(MQuoteSupplierRepository mQuoteSupplierRepository, MQuoteSupplierMapper mQuoteSupplierMapper) {
        this.mQuoteSupplierRepository = mQuoteSupplierRepository;
        this.mQuoteSupplierMapper = mQuoteSupplierMapper;
    }

    /**
     * Save a mQuoteSupplier.
     *
     * @param mQuoteSupplierDTO the entity to save.
     * @return the persisted entity.
     */
    public MQuoteSupplierDTO save(MQuoteSupplierDTO mQuoteSupplierDTO) {
        log.debug("Request to save MQuoteSupplier : {}", mQuoteSupplierDTO);
        MQuoteSupplier mQuoteSupplier = mQuoteSupplierMapper.toEntity(mQuoteSupplierDTO);
        mQuoteSupplier = mQuoteSupplierRepository.save(mQuoteSupplier);
        return mQuoteSupplierMapper.toDto(mQuoteSupplier);
    }

    /**
     * Get all the mQuoteSuppliers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MQuoteSupplierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MQuoteSuppliers");
        return mQuoteSupplierRepository.findAll(pageable)
            .map(mQuoteSupplierMapper::toDto);
    }

    /**
     * Get one mQuoteSupplier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MQuoteSupplierDTO> findOne(Long id) {
        log.debug("Request to get MQuoteSupplier : {}", id);
        return mQuoteSupplierRepository.findById(id)
            .map(mQuoteSupplierMapper::toDto);
    }

    /**
     * Delete the mQuoteSupplier by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MQuoteSupplier : {}", id);
        mQuoteSupplierRepository.deleteById(id);
    }

    public List<MQuoteSupplierDTO> saveAll(List<MQuoteSupplierDTO> mQuoteSupplierDTOs) {
        for(MQuoteSupplierDTO dto: mQuoteSupplierDTOs){
            Optional<MQuoteSupplier> find = mQuoteSupplierRepository.findByQuotation_idAndVendor_id(dto.getQuotationId(), dto.getVendorId());
            if(find.isPresent()) continue;
            else {
                save(dto);
            }
        }
        return mQuoteSupplierDTOs;
    }
}
