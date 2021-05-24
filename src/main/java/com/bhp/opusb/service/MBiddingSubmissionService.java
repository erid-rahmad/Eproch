package com.bhp.opusb.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.repository.MBiddingSubmissionRepository;
import com.bhp.opusb.service.dto.MBiddingScheduleCriteria;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;
import com.bhp.opusb.service.mapper.MBiddingSubmissionMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;

/**
 * Service Implementation for managing {@link MBiddingSubmission}.
 */
@Service
@Transactional
public class MBiddingSubmissionService {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubmissionService.class);

    private final MBiddingScheduleQueryService mBiddingScheduleQueryService;

    private final MBiddingSubmissionRepository mBiddingSubmissionRepository;

    private final MBiddingSubmissionMapper mBiddingSubmissionMapper;

    private final Document document;

    public MBiddingSubmissionService(ApplicationProperties applicationProperties,
            MBiddingScheduleQueryService mBiddingScheduleQueryService,
            MBiddingSubmissionRepository mBiddingSubmissionRepository,
            MBiddingSubmissionMapper mBiddingSubmissionMapper) {
        this.mBiddingScheduleQueryService = mBiddingScheduleQueryService;
        this.mBiddingSubmissionRepository = mBiddingSubmissionRepository;
        this.mBiddingSubmissionMapper = mBiddingSubmissionMapper;

        document = applicationProperties.getDocuments().get("biddingSubmission");
    }

    /**
     * Save a mBiddingSubmission.
     *
     * @param mBiddingSubmissionDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingSubmissionDTO save(MBiddingSubmissionDTO mBiddingSubmissionDTO) {
        log.debug("Request to save MBiddingSubmission : {}", mBiddingSubmissionDTO);
        MBiddingSubmission mBiddingSubmission = mBiddingSubmissionMapper.toEntity(mBiddingSubmissionDTO);
        
        if (mBiddingSubmission.getDocumentNo() == null) {
            String documentNo = DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mBiddingSubmissionRepository);
            mBiddingSubmission.setDocumentNo(documentNo);
        }

        mBiddingSubmission = mBiddingSubmissionRepository.save(mBiddingSubmission);

        return mBiddingSubmissionMapper.toDto(mBiddingSubmission);
    }

    /**
     * Create a new MBiddingSubmission for the given MBidding, CVendor, and CEventTypeLine sequence number.
     * @param mBidding
     * @param cVendor
     * @param currentEventSequence
     */
    public void createIfNotExists(MBidding mBidding, CVendor cVendor, Integer currentEventSequence) {
        Optional<MBiddingSubmission> submission = mBiddingSubmissionRepository
        .findFirstByBiddingAndVendor(mBidding, cVendor);

        if (!submission.isPresent()) {
            MBiddingScheduleCriteria mBiddingScheduleCriteria = new MBiddingScheduleCriteria();
            LongFilter biddingIdFilter = new LongFilter();
            IntegerFilter sequenceFilter = new IntegerFilter();
            biddingIdFilter.setEquals(mBidding.getId());
            sequenceFilter.setGreaterThan(currentEventSequence);
            mBiddingScheduleCriteria.setBiddingId(biddingIdFilter);
            mBiddingScheduleCriteria.setSequence(sequenceFilter);

            Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "eventTypeLine.sequence"));
            Page<MBiddingScheduleDTO> mBiddingScheduleDTOs = mBiddingScheduleQueryService.findByCriteria(mBiddingScheduleCriteria, pageable);
            List<MBiddingScheduleDTO> scheduleList = mBiddingScheduleDTOs.getContent();

            if ( ! scheduleList.isEmpty()) {
                String documentNo = DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mBiddingSubmissionRepository);
                Long biddingScheduleId = scheduleList.get(0).getId();
                MBiddingSubmissionDTO mBiddingSubmission = new MBiddingSubmissionDTO();
                mBiddingSubmission.setDocumentNo(documentNo);
                mBiddingSubmission.setAdOrganizationId(mBidding.getAdOrganization().getId());
                mBiddingSubmission.setBiddingId(mBidding.getId());
                mBiddingSubmission.setBiddingScheduleId(biddingScheduleId);
                mBiddingSubmission.setVendorId(cVendor.getId());
                mBiddingSubmissionRepository.save(mBiddingSubmissionMapper.toEntity(mBiddingSubmission));
            }
        }
    }

    /**
     * TODO Use a generic method to update the document status for every entities.
     * TODO Use the workflow engine for maintaining the flow state.
     */
    public void updateDocumentStatus(MBiddingSubmissionDTO mBiddingSubmissionDTO) {
        log.debug("Request to update CVendor's document status : {}", mBiddingSubmissionDTO);
        MBiddingSubmission mBiddingSubmission = mBiddingSubmissionMapper.toEntity(mBiddingSubmissionDTO);
        String action = mBiddingSubmission.getDocumentAction();

        // TODO The following process is less-efficient. Make this more efficient.
        if (mBiddingSubmission.getDateSubmit() == null && DocumentUtil.isSubmit(action)) {
            mBiddingSubmission.setDateSubmit(ZonedDateTime.now());
            mBiddingSubmissionRepository.save(mBiddingSubmission);
        }

        mBiddingSubmissionRepository.updateDocumentStatus(mBiddingSubmission.getId(), action, action, false, false);
    }

    /**
     * Get all the mBiddingSubmissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingSubmissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingSubmissions");
        return mBiddingSubmissionRepository.findAll(pageable)
            .map(mBiddingSubmissionMapper::toDto);
    }

    /**
     * Get one mBiddingSubmission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingSubmissionDTO> findOne(Long id) {
        log.debug("Request to get MBiddingSubmission : {}", id);
        return mBiddingSubmissionRepository.findById(id)
            .map(mBiddingSubmissionMapper::toDto);
    }

    /**
     * Delete the mBiddingSubmission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingSubmission : {}", id);
        mBiddingSubmissionRepository.deleteById(id);
    }
}
