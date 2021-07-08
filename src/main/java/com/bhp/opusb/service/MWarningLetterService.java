package com.bhp.opusb.service;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.MWarningLetter;
import com.bhp.opusb.repository.MWarningLetterRepository;
import com.bhp.opusb.service.dto.MWarningLetterDTO;
import com.bhp.opusb.service.mapper.MWarningLetterMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MWarningLetter}.
 */
@Service
@Transactional
public class MWarningLetterService {

    private final Logger log = LoggerFactory.getLogger(MWarningLetterService.class);

    private final MWarningLetterRepository mWarningLetterRepository;

    private final MWarningLetterMapper mWarningLetterMapper;

    private final Document document;
    
    public MWarningLetterService(ApplicationProperties applicationProperties, 
        MWarningLetterRepository mWarningLetterRepository, MWarningLetterMapper mWarningLetterMapper) {
        this.mWarningLetterRepository = mWarningLetterRepository;
        this.mWarningLetterMapper = mWarningLetterMapper;

        document = applicationProperties.getDocuments().get("warningLetter");
    }

    private String initDocumentNumber() {
        return DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mWarningLetterRepository);
    }

    /**
     * Save a mWarningLetter.
     *
     * @param mWarningLetterDTO the entity to save.
     * @return the persisted entity.
     */
    public MWarningLetterDTO save(MWarningLetterDTO mWarningLetterDTO) {
        log.debug("Request to save MWarningLetter : {}", mWarningLetterDTO);
        MWarningLetter mWarningLetter = mWarningLetterMapper.toEntity(mWarningLetterDTO);

        if (mWarningLetter.getDocumentNo() == null) {
            mWarningLetter.documentNo(initDocumentNumber());
        }

        mWarningLetter = mWarningLetterRepository.save(mWarningLetter);
        return mWarningLetterMapper.toDto(mWarningLetter);
    }

    /**
     * Get all the mWarningLetters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MWarningLetterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MWarningLetters");
        return mWarningLetterRepository.findAll(pageable)
            .map(mWarningLetterMapper::toDto);
    }

    /**
     * Get one mWarningLetter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MWarningLetterDTO> findOne(Long id) {
        log.debug("Request to get MWarningLetter : {}", id);
        return mWarningLetterRepository.findById(id)
            .map(mWarningLetterMapper::toDto);
    }

    /**
     * Delete the mWarningLetter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MWarningLetter : {}", id);
        mWarningLetterRepository.deleteById(id);
    }
}
