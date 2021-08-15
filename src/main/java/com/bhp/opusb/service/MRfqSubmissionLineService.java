package com.bhp.opusb.service;

import com.bhp.opusb.domain.MRfqSubmissionLine;
import com.bhp.opusb.repository.MRfqSubmissionLineRepository;
import com.bhp.opusb.service.dto.MRfqSubmissionLineDTO;
import com.bhp.opusb.service.mapper.MRfqSubmissionLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MRfqSubmissionLine}.
 */
@Service
@Transactional
public class MRfqSubmissionLineService {

    private final Logger log = LoggerFactory.getLogger(MRfqSubmissionLineService.class);

    private final MRfqSubmissionLineRepository mRfqSubmissionLineRepository;

    private final MRfqSubmissionLineMapper mRfqSubmissionLineMapper;

    public MRfqSubmissionLineService(MRfqSubmissionLineRepository mRfqSubmissionLineRepository, MRfqSubmissionLineMapper mRfqSubmissionLineMapper) {
        this.mRfqSubmissionLineRepository = mRfqSubmissionLineRepository;
        this.mRfqSubmissionLineMapper = mRfqSubmissionLineMapper;
    }

    /**
     * Save a mRfqSubmissionLine.
     *
     * @param mRfqSubmissionLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MRfqSubmissionLineDTO save(MRfqSubmissionLineDTO mRfqSubmissionLineDTO) {
        log.debug("Request to save MRfqSubmissionLine : {}", mRfqSubmissionLineDTO);
        MRfqSubmissionLine mRfqSubmissionLine = mRfqSubmissionLineMapper.toEntity(mRfqSubmissionLineDTO);
        mRfqSubmissionLine = mRfqSubmissionLineRepository.save(mRfqSubmissionLine);
        return mRfqSubmissionLineMapper.toDto(mRfqSubmissionLine);
    }

    /**
     * Get all the mRfqSubmissionLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MRfqSubmissionLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MRfqSubmissionLines");
        return mRfqSubmissionLineRepository.findAll(pageable)
            .map(mRfqSubmissionLineMapper::toDto);
    }

    /**
     * Get one mRfqSubmissionLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MRfqSubmissionLineDTO> findOne(Long id) {
        log.debug("Request to get MRfqSubmissionLine : {}", id);
        return mRfqSubmissionLineRepository.findById(id)
            .map(mRfqSubmissionLineMapper::toDto);
    }

    /**
     * Delete the mRfqSubmissionLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MRfqSubmissionLine : {}", id);
        mRfqSubmissionLineRepository.deleteById(id);
    }

    public Optional<MRfqSubmissionLineDTO> findBySubmissionIdAndRfqLineId(Long submissionId, Long quotationLineId) {
        return mRfqSubmissionLineRepository
            .findFirstBySubmission_IdAndQuotationLine_Id(submissionId, quotationLineId).map(mRfqSubmissionLineMapper::toDto);
    }
}
