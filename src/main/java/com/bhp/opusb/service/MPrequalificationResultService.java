package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalificationResult;
import com.bhp.opusb.repository.MPrequalificationResultRepository;
import com.bhp.opusb.service.dto.AdUserDTO;
import com.bhp.opusb.service.dto.MPrequalResultPublish;
import com.bhp.opusb.service.dto.MPrequalificationResultDTO;
import com.bhp.opusb.service.dto.MPrequalificationSubmissionDTO;
import com.bhp.opusb.service.mapper.MPrequalificationResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Implementation for managing {@link MPrequalificationResult}.
 */
@Service
@Transactional
public class MPrequalificationResultService {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationResultService.class);

    private final MPrequalificationResultRepository mPrequalificationResultRepository;

    private final MPrequalificationResultMapper mPrequalificationResultMapper;

    public MPrequalificationResultService(MPrequalificationResultRepository mPrequalificationResultRepository, MPrequalificationResultMapper mPrequalificationResultMapper) {
        this.mPrequalificationResultRepository = mPrequalificationResultRepository;
        this.mPrequalificationResultMapper = mPrequalificationResultMapper;
    }

    @Autowired
    MailService mailService;

    @Value("${application.attachment.upload-dir}")
    private String UploadDir;

    /**
     * Save a mPrequalificationResult.
     *
     * @param mPrequalificationResultDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalificationResultDTO save(MPrequalificationResultDTO mPrequalificationResultDTO) {
        log.debug("Request to save MPrequalificationResult : {}", mPrequalificationResultDTO);
        MPrequalificationResult mPrequalificationResult = mPrequalificationResultMapper.toEntity(mPrequalificationResultDTO);
        mPrequalificationResult = mPrequalificationResultRepository.save(mPrequalificationResult);
        return mPrequalificationResultMapper.toDto(mPrequalificationResult);
    }

    /**
     * Get all the mPrequalificationResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalificationResults");
        return mPrequalificationResultRepository.findAll(pageable)
            .map(mPrequalificationResultMapper::toDto);
    }

    /**
     * Get one mPrequalificationResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalificationResultDTO> findOne(Long id) {
        log.debug("Request to get MPrequalificationResult : {}", id);
        Optional<MPrequalificationResult> mPrequalificationResult = mPrequalificationResultRepository.findById(id);

        //log.info("this bidding schedule {}",mBiddingResult.get().getBiddingEvalResult().getBiddingSubmission().getBiddingSchedule());
        //mBiddingResult.get().getBiddingEvalResult().getBiddingSubmission().getBiddingSchedule();
        String email= mPrequalificationResult.get().getSubmission().getPassFail().toLowerCase().contentEquals("pass") ?
            mPrequalificationResult.get().getAnnouncementResult().getDescriptionPass() : 
            mPrequalificationResult.get().getAnnouncementResult().getDescriptionFail();
        
        email=email.replace("#vendorName",mPrequalificationResult.get().getVendor().getName());//getBiddingEvalResult().getBiddingSubmission().getVendor().getName());
        email=email.replace("#status",mPrequalificationResult.get().getSubmission().getPassFail());
        email=email.replace("#email",mPrequalificationResult.get().getVendor().getEmail()==null?"":mPrequalificationResult.get().getVendor().getEmail());
        Optional<MPrequalificationResultDTO> mPrequalificationResultDTO = mPrequalificationResult.map(mPrequalificationResultMapper::toDto);
        mPrequalificationResultDTO.get().setAnnouncementResultName(email);
        mPrequalificationResultDTO.get().setStartDate(mPrequalificationResult.get().getAnnouncementResult().getPrequalificationSchedule().getStartDate());
        mPrequalificationResultDTO.get().setEndDate(mPrequalificationResult.get().getAnnouncementResult().getPrequalificationSchedule().getEndDate());
        return mPrequalificationResultDTO;
    }

    /**
     * Delete the mPrequalificationResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalificationResult : {}", id);
        mPrequalificationResultRepository.deleteById(id);
    }

    public MPrequalResultPublish publish(@Valid MPrequalResultPublish mPrequalResultPublish) {
        for(MPrequalificationSubmissionDTO mPrequalificationSubmission_:mPrequalResultPublish.getmPrequalificationSubmission()){
            MPrequalificationResultDTO mPrequalificationResultDTO = new MPrequalificationResultDTO();
            mPrequalificationResultDTO.setPrequalificationId(mPrequalificationSubmission_.getPrequalificationId());
            mPrequalificationResultDTO.setVendorId(mPrequalificationSubmission_.getVendorId());
            mPrequalificationResultDTO.setAnnouncementResultId(mPrequalResultPublish.getmPrequalAnnouncementResultDTO().getId());
            mPrequalificationResultDTO.setSubmissionId(mPrequalificationSubmission_.getId());
            mPrequalificationResultDTO.setActive(true);
            mPrequalificationResultDTO.setAdOrganizationId(1l);
            save(mPrequalificationResultDTO);
        }

        for (AdUserDTO adUserDTO :mPrequalResultPublish.getUsers()){
            MPrequalificationSubmissionDTO sub = null;
            for(MPrequalificationSubmissionDTO mPrequalificationSubmission_:mPrequalResultPublish.getmPrequalificationSubmission()){
                if(mPrequalificationSubmission_.getVendorId().equals(adUserDTO.getcVendorId())){
                    sub = mPrequalificationSubmission_;
                    break;
                }
            }

            String body = sub.getPassFail().toLowerCase().contentEquals("pass") ? 
                mPrequalResultPublish.getmPrequalAnnouncementResultDTO().getDescriptionPass().replace("#vendorName",adUserDTO.getcVendorName()).replace("#email", adUserDTO.getEmail()==null?"":adUserDTO.getEmail()):
                mPrequalResultPublish.getmPrequalAnnouncementResultDTO().getDescriptionFail().replace("#vendorName",adUserDTO.getcVendorName()).replace("#email", adUserDTO.getEmail()==null?"":adUserDTO.getEmail());

            mailService.sendMailWithAttachment(adUserDTO.getEmail(), "Prequalification Evaluation Result", body, false, true,
                UploadDir+"/"+mPrequalResultPublish.getmPrequalAnnouncementResultDTO().getAttachmentName());
        }

        return mPrequalResultPublish;
    }
}
