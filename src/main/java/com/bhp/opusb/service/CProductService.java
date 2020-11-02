package com.bhp.opusb.service;

import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.repository.CProductRepository;
import com.bhp.opusb.service.dto.CProductDTO;
import com.bhp.opusb.service.mapper.CProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CProduct}.
 */
@Service
@Transactional
public class CProductService {

    private final Logger log = LoggerFactory.getLogger(CProductService.class);

    private final CProductRepository cProductRepository;

    private final CProductMapper cProductMapper;

    public CProductService(CProductRepository cProductRepository, CProductMapper cProductMapper) {
        this.cProductRepository = cProductRepository;
        this.cProductMapper = cProductMapper;
    }

    /**
     * Save a cProduct.
     *
     * @param cProductDTO the entity to save.
     * @return the persisted entity.
     */
    public CProductDTO save(CProductDTO cProductDTO) {
        log.debug("Request to save CProduct : {}", cProductDTO);
        CProduct cProduct = cProductMapper.toEntity(cProductDTO);
        cProduct = cProductRepository.save(cProduct);
        return cProductMapper.toDto(cProduct);
    }

    /**
     * Get all the cProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CProducts");
        return cProductRepository.findAll(pageable)
            .map(cProductMapper::toDto);
    }

    /**
     * Get one cProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CProductDTO> findOne(Long id) {
        log.debug("Request to get CProduct : {}", id);
        return cProductRepository.findById(id)
            .map(cProductMapper::toDto);
    }

    /**
     * Delete the cProduct by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CProduct : {}", id);
        cProductRepository.deleteById(id);
    }
}
