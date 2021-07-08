package com.bhp.opusb.service;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.MComplaint;
import com.bhp.opusb.repository.MComplaintRepository;
import com.bhp.opusb.service.dto.MComplaintDTO;
import com.bhp.opusb.service.mapper.MComplaintMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MComplaint}.
 */
@Service
@Transactional
public class MComplaintService {

    private final Logger log = LoggerFactory.getLogger(MComplaintService.class);

    private final MComplaintRepository mComplaintRepository;

    private final MComplaintMapper mComplaintMapper;

    private final Document document;

    public MComplaintService(ApplicationProperties applicationProperties, 
    MComplaintRepository mComplaintRepository, MComplaintMapper mComplaintMapper) {
        this.mComplaintRepository = mComplaintRepository;
        this.mComplaintMapper = mComplaintMapper;
        document = applicationProperties.getDocuments().get("complaint");
    }

    private String initDocumentNumber() {
        return DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mComplaintRepository);
    }

    /**
     * Save a mComplaint.
     *
     * @param mComplaintDTO the entity to save.
     * @return the persisted entity.
     */
    public MComplaintDTO save(MComplaintDTO mComplaintDTO) {
        log.debug("Request to save MComplaint : {}", mComplaintDTO);
        MComplaint mComplaint = mComplaintMapper.toEntity(mComplaintDTO);

        if (mComplaint.getDocumentNo() == null) {
            mComplaint.documentNo(initDocumentNumber());
        }

        mComplaint = mComplaintRepository.save(mComplaint);
        return mComplaintMapper.toDto(mComplaint);
    }

    /**
     * Get all the mComplaints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MComplaintDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MComplaints");
        return mComplaintRepository.findAll(pageable)
            .map(mComplaintMapper::toDto);
    }

    /**
     * Get one mComplaint by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MComplaintDTO> findOne(Long id) {
        log.debug("Request to get MComplaint : {}", id);
        return mComplaintRepository.findById(id)
            .map(mComplaintMapper::toDto);
    }

    /**
     * Delete the mComplaint by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MComplaint : {}", id);
        mComplaintRepository.deleteById(id);
    }
}
