package com.bhp.opusb.service;

import com.bhp.opusb.domain.MRfqSubmission;
import com.bhp.opusb.repository.MRfqSubmissionRepository;
import com.bhp.opusb.service.dto.MRfqSubmissionDTO;
import com.bhp.opusb.service.dto.MRfqSubmissionLineDTO;
import com.bhp.opusb.service.mapper.MRfqSubmissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MRfqSubmission}.
 */
@Service
@Transactional
public class MRfqSubmissionService {

    private final Logger log = LoggerFactory.getLogger(MRfqSubmissionService.class);

    private final MRfqSubmissionRepository mRfqSubmissionRepository;
    private final MRfqSubmissionLineService mRfqSubmissionLineService;

    private final MRfqSubmissionMapper mRfqSubmissionMapper;

    public MRfqSubmissionService(MRfqSubmissionRepository mRfqSubmissionRepository, MRfqSubmissionMapper mRfqSubmissionMapper,
        MRfqSubmissionLineService mRfqSubmissionLineService) {
        this.mRfqSubmissionRepository = mRfqSubmissionRepository;
        this.mRfqSubmissionMapper = mRfqSubmissionMapper;
        this.mRfqSubmissionLineService = mRfqSubmissionLineService;
    }

    /**
     * Save a mRfqSubmission.
     *
     * @param mRfqSubmissionDTO the entity to save.
     * @return the persisted entity.
     */
    public MRfqSubmissionDTO save(MRfqSubmissionDTO mRfqSubmissionDTO) {
        log.debug("Request to save MRfqSubmission : {}", mRfqSubmissionDTO);
        MRfqSubmission mRfqSubmission = mRfqSubmissionMapper.toEntity(mRfqSubmissionDTO);
        mRfqSubmission = mRfqSubmissionRepository.save(mRfqSubmission);

        if(mRfqSubmissionDTO.getLine()!=null) {
            for(MRfqSubmissionLineDTO line: mRfqSubmissionDTO.getLine()) {
                if(line.getSubmissionId()==null) {
                    line.setSubmissionId(mRfqSubmission.getId());
                    Optional<MRfqSubmissionLineDTO> dupeCheck = 
                        mRfqSubmissionLineService.findBySubmissionIdAndRfqLineId(line.getSubmissionId(),line.getQuotationLineId());
                    if(dupeCheck.isPresent()){
                        line.setId(dupeCheck.get().getId());
                    }
                }
                mRfqSubmissionLineService.save(line);
            }
        }
        return mRfqSubmissionMapper.toDto(mRfqSubmission);
    }

    /**
     * Get all the mRfqSubmissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MRfqSubmissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MRfqSubmissions");
        return mRfqSubmissionRepository.findAll(pageable)
            .map(mRfqSubmissionMapper::toDto);
    }

    /**
     * Get one mRfqSubmission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MRfqSubmissionDTO> findOne(Long id) {
        log.debug("Request to get MRfqSubmission : {}", id);
        return mRfqSubmissionRepository.findById(id)
            .map(mRfqSubmissionMapper::toDto);
    }

    /**
     * Delete the mRfqSubmission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MRfqSubmission : {}", id);
        mRfqSubmissionRepository.deleteById(id);
    }
}
