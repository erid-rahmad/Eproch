package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CVendorBusinessCat;
import com.bhp.opusb.repository.CVendorBusinessCatRepository;
import com.bhp.opusb.service.dto.CVendorBusinessCatDTO;
import com.bhp.opusb.service.mapper.CVendorBusinessCatMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CVendorBusinessCat}.
 */
@Service
@Transactional
public class CVendorBusinessCatService {

    private final Logger log = LoggerFactory.getLogger(CVendorBusinessCatService.class);

    private final CVendorBusinessCatRepository cVendorBusinessCatRepository;

    private final CVendorBusinessCatMapper cVendorBusinessCatMapper;

    public CVendorBusinessCatService(CVendorBusinessCatRepository cVendorBusinessCatRepository, CVendorBusinessCatMapper cVendorBusinessCatMapper) {
        this.cVendorBusinessCatRepository = cVendorBusinessCatRepository;
        this.cVendorBusinessCatMapper = cVendorBusinessCatMapper;
    }

    /**
     * Save a cVendorBusinessCat.
     *
     * @param cVendorBusinessCatDTO the entity to save.
     * @return the persisted entity.
     */
    public CVendorBusinessCatDTO save(CVendorBusinessCatDTO cVendorBusinessCatDTO) {
        log.debug("Request to save CVendorBusinessCat : {}", cVendorBusinessCatDTO);
        CVendorBusinessCat cVendorBusinessCat = cVendorBusinessCatMapper.toEntity(cVendorBusinessCatDTO);
        cVendorBusinessCat = cVendorBusinessCatRepository.save(cVendorBusinessCat);
        return cVendorBusinessCatMapper.toDto(cVendorBusinessCat);
    }

    public List<CVendorBusinessCatDTO> saveAll(List<Long> businessCategoryIds, CVendor vendor, ADOrganization organization) {
        log.debug("Request to save CVendorBusinessCats. List size: {}", businessCategoryIds.size());
        List<CVendorBusinessCat> businessCategories = businessCategoryIds
            .stream()
            .map(id -> {
                CBusinessCategory businessCategory = new CBusinessCategory();
                businessCategory.setId(id);
                
                return new CVendorBusinessCat()
                    .active(true)
                    .adOrganization(organization)
                    .vendor(vendor)
                    .businessCategory(businessCategory);
            })
            .collect(Collectors.toList());

        return cVendorBusinessCatRepository.saveAll(businessCategories)
            .stream()
            .map(cVendorBusinessCatMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all the cVendorBusinessCats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorBusinessCatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CVendorBusinessCats");
        return cVendorBusinessCatRepository.findAll(pageable)
            .map(cVendorBusinessCatMapper::toDto);
    }

    /**
     * Get one cVendorBusinessCat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CVendorBusinessCatDTO> findOne(Long id) {
        log.debug("Request to get CVendorBusinessCat : {}", id);
        return cVendorBusinessCatRepository.findById(id)
            .map(cVendorBusinessCatMapper::toDto);
    }

    /**
     * Delete the cVendorBusinessCat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CVendorBusinessCat : {}", id);
        cVendorBusinessCatRepository.deleteById(id);
    }
}
