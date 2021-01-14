package com.bhp.opusb.service;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.MProductCatalog;
import com.bhp.opusb.repository.MProductCatalogRepository;
import com.bhp.opusb.service.dto.MProductCatalogDTO;
import com.bhp.opusb.service.dto.marketplace.BhinnekaItemFilterDTO;
import com.bhp.opusb.service.mapper.MProductCatalogMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link MProductCatalog}.
 */
@Service
@Transactional
public class MProductCatalogService {

    private final Logger log = LoggerFactory.getLogger(MProductCatalogService.class);

    private final MProductCatalogRepository mProductCatalogRepository;

    private final MProductCatalogMapper mProductCatalogMapper;

    private final ObjectMapper jsonMapper;

    public MProductCatalogService(MProductCatalogRepository mProductCatalogRepository,
            MProductCatalogMapper mProductCatalogMapper, ObjectMapper jsonMapper) {
        this.mProductCatalogRepository = mProductCatalogRepository;
        this.mProductCatalogMapper = mProductCatalogMapper;
        this.jsonMapper = jsonMapper;
    }

    /**
     * Save a mProductCatalog.
     *
     * @param mProductCatalogDTO the entity to save.
     * @return the persisted entity.
     */
    public MProductCatalogDTO save(MProductCatalogDTO mProductCatalogDTO) {
        log.debug("Request to save MProductCatalog : {}", mProductCatalogDTO);
        MProductCatalog mProductCatalog = mProductCatalogMapper.toEntity(mProductCatalogDTO);
        mProductCatalog = mProductCatalogRepository.save(mProductCatalog);
        return mProductCatalogMapper.toDto(mProductCatalog);
    }

    /**
     * Get all the mProductCatalogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProductCatalogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProductCatalogs");
        return mProductCatalogRepository.findAll(pageable)
            .map(mProductCatalogMapper::toDto);
    }

    /**
     * Get one mProductCatalog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProductCatalogDTO> findOne(Long id) {
        log.debug("Request to get MProductCatalog : {}", id);
        return mProductCatalogRepository.findById(id)
            .map(mProductCatalogMapper::toDto);
    }

    /**
     * Delete the mProductCatalog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProductCatalog : {}", id);
        mProductCatalogRepository.deleteById(id);
    }

    public int importBhinnekaCatalog(MultipartFile file) {
        int importedRows = 0;
        BhinnekaItemFilterDTO data = null;

        try {
            data = jsonMapper.readValue(file.getInputStream(), BhinnekaItemFilterDTO.class);
            
            if (data != null && data.getFilter() != null && data.getFilter().getResult() != null) {
                mProductCatalogRepository.saveAll(
                    data.getFilter().getResult().stream()
                        .map(item -> mProductCatalogMapper.toEntity(item.toDto()))
                        .collect(Collectors.toList())
                );
                importedRows = data.getFilter().getResult().size();
            }
        } catch (JsonParseException e) {
            log.warn("Failed when parsing the uploaded JSON file", e);
        } catch (JsonMappingException e) {
            log.warn("Failed when mapping the uploaded JSON file", e);
        } catch (IOException e) {
            log.warn("Failed when processing the uploaded JSON file", e);
        }
        
        return importedRows;
    }
}
