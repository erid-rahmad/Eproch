package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPreqRegistDocument;
import com.bhp.opusb.domain.MPrequalRegistration;
import com.bhp.opusb.repository.MPreqRegistDocumentRepository;
import com.bhp.opusb.repository.MPrequalRegistrationRepository;
import com.bhp.opusb.service.dto.MPreqRegistDocumentDTO;
import com.bhp.opusb.service.mapper.MPreqRegistDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MPreqRegistDocument}.
 */
@Service
@Transactional
public class MPreqRegistDocumentService {

    private final Logger log = LoggerFactory.getLogger(MPreqRegistDocumentService.class);

    private final MPreqRegistDocumentRepository mPreqRegistDocumentRepository;
    private final MPrequalRegistrationRepository mPrequalRegistrationRepository;

    private final MPreqRegistDocumentMapper mPreqRegistDocumentMapper;

    public MPreqRegistDocumentService(MPreqRegistDocumentRepository mPreqRegistDocumentRepository, 
        MPreqRegistDocumentMapper mPreqRegistDocumentMapper, MPrequalRegistrationRepository mPrequalRegistrationRepository) {
        this.mPreqRegistDocumentRepository = mPreqRegistDocumentRepository;
        this.mPreqRegistDocumentMapper = mPreqRegistDocumentMapper;
        this.mPrequalRegistrationRepository = mPrequalRegistrationRepository;
    }

    /**
     * Save a mPreqRegistDocument.
     *
     * @param mPreqRegistDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public MPreqRegistDocumentDTO save(MPreqRegistDocumentDTO mPreqRegistDocumentDTO) {
        log.debug("Request to save MPreqRegistDocument : {}", mPreqRegistDocumentDTO);
        MPreqRegistDocument mPreqRegistDocument = mPreqRegistDocumentMapper.toEntity(mPreqRegistDocumentDTO);
        mPreqRegistDocument = mPreqRegistDocumentRepository.save(mPreqRegistDocument);

        MPrequalRegistration reg = mPrequalRegistrationRepository.findById(mPreqRegistDocumentDTO.getRegistrationId()).get();
        reg.setRegistrationStatus("R");
        reg.setAnswerDate(ZonedDateTime.now());
        mPrequalRegistrationRepository.save(reg);

        return mPreqRegistDocumentMapper.toDto(mPreqRegistDocument);
    }

    /**
     * Get all the mPreqRegistDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPreqRegistDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPreqRegistDocuments");
        return mPreqRegistDocumentRepository.findAll(pageable)
            .map(mPreqRegistDocumentMapper::toDto);
    }

    /**
     * Get one mPreqRegistDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPreqRegistDocumentDTO> findOne(Long id) {
        log.debug("Request to get MPreqRegistDocument : {}", id);
        return mPreqRegistDocumentRepository.findById(id)
            .map(mPreqRegistDocumentMapper::toDto);
    }

    /**
     * Delete the mPreqRegistDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPreqRegistDocument : {}", id);
        mPreqRegistDocumentRepository.deleteById(id);
    }
}
