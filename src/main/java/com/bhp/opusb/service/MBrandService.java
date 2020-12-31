package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBrand;
import com.bhp.opusb.repository.MBrandRepository;
import com.bhp.opusb.service.dto.MBrandDTO;
import com.bhp.opusb.service.mapper.MBrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBrand}.
 */
@Service
@Transactional
public class MBrandService {

    private final Logger log = LoggerFactory.getLogger(MBrandService.class);

    private final MBrandRepository mBrandRepository;

    private final MBrandMapper mBrandMapper;

    public MBrandService(MBrandRepository mBrandRepository, MBrandMapper mBrandMapper) {
        this.mBrandRepository = mBrandRepository;
        this.mBrandMapper = mBrandMapper;
    }

    /**
     * Save a mBrand.
     *
     * @param mBrandDTO the entity to save.
     * @return the persisted entity.
     */
    public MBrandDTO save(MBrandDTO mBrandDTO) {
        log.debug("Request to save MBrand : {}", mBrandDTO);
        MBrand mBrand = mBrandMapper.toEntity(mBrandDTO);
        mBrand = mBrandRepository.save(mBrand);
        return mBrandMapper.toDto(mBrand);
    }

    /**
     * Get all the mBrands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBrandDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBrands");
        return mBrandRepository.findAll(pageable)
            .map(mBrandMapper::toDto);
    }


    /**
     * Get one mBrand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBrandDTO> findOne(Long id) {
        log.debug("Request to get MBrand : {}", id);
        return mBrandRepository.findById(id)
            .map(mBrandMapper::toDto);
    }

    /**
     * Delete the mBrand by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBrand : {}", id);
        mBrandRepository.deleteById(id);
    }
}
