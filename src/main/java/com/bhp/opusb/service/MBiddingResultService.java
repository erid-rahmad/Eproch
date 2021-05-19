package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.MBiddingEvalResult;
import com.bhp.opusb.domain.MBiddingResult;
import com.bhp.opusb.repository.MBiddingResultRepository;
import com.bhp.opusb.service.dto.AdUserDTO;
import com.bhp.opusb.service.dto.MBiddingEvalResultDTO;
import com.bhp.opusb.service.dto.MBiddingResultDTO;
import com.bhp.opusb.service.dto.MBiddingResultPublish;
import com.bhp.opusb.service.mapper.MBiddingResultMapper;
import com.bhp.opusb.util.MapperJSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingResult}.
 */
@Service
@Transactional
public class MBiddingResultService {

    private final Logger log = LoggerFactory.getLogger(MBiddingResultService.class);

    private final MBiddingResultRepository mBiddingResultRepository;

    private final MBiddingResultMapper mBiddingResultMapper;

    public MBiddingResultService(MBiddingResultRepository mBiddingResultRepository, MBiddingResultMapper mBiddingResultMapper) {
        this.mBiddingResultRepository = mBiddingResultRepository;
        this.mBiddingResultMapper = mBiddingResultMapper;
    }

    @Autowired
    MailService mailService;

    @Value("${application.attachment.upload-dir}")
    private String UploadDir;

    /**
     * Save a mBiddingResult.
     *
     * @param mBiddingResultDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingResultDTO save(MBiddingResultDTO mBiddingResultDTO) {
        log.debug("Request to save MBiddingResult : {}", mBiddingResultDTO);
        MBiddingResult mBiddingResult = mBiddingResultMapper.toEntity(mBiddingResultDTO);
        mBiddingResult = mBiddingResultRepository.save(mBiddingResult);
        return mBiddingResultMapper.toDto(mBiddingResult);
    }

    public MBiddingResultPublish publish(MBiddingResultPublish mBiddingResultPublish) {
        log.debug("this debug 1 {}", MapperJSONUtil.prettyLog(mBiddingResultPublish));


        for(MBiddingEvalResultDTO mBiddingEvalResult_:mBiddingResultPublish.getmBiddingEvalResult()){
            log.debug("this debug 2 {}",MapperJSONUtil.prettyLog(mBiddingEvalResult_));
            MBiddingResultDTO mBiddingResultDTO =new MBiddingResultDTO();
            mBiddingResultDTO.setBiddingId(mBiddingEvalResult_.getBiddingId());
            mBiddingResultDTO.setVendorId(mBiddingEvalResult_.getVendorId());
            mBiddingResultDTO.setAnnouncementResultId(mBiddingResultPublish.getcAnnouncementResultDTO().getId());
            mBiddingResultDTO.setBiddingEvalResultId(mBiddingEvalResult_.getId());
            mBiddingResultDTO.setActive(true);
            mBiddingResultDTO.setAdOrganizationId(1l);
            save(mBiddingResultDTO);
        }

        for (AdUserDTO adUserDTO :mBiddingResultPublish.getUsers()){
            String body = mBiddingResultPublish.getcAnnouncementResultDTO().getDescription()
                .replace("#email", "replace");
            mailService.sendMailWithAttachment(adUserDTO.getEmail(), "Bidding Invitation", body, false, true,
                UploadDir+"/"+mBiddingResultPublish.getcAnnouncementResultDTO().getAttachmentName());
        }



        return mBiddingResultPublish;
    }

    /**
     * Get all the mBiddingResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingResults");
        return mBiddingResultRepository.findAll(pageable)
            .map(mBiddingResultMapper::toDto);
    }

    /**
     * Get one mBiddingResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingResultDTO> findOne(Long id) {
        log.debug("Request to get MBiddingResult : {}", id);
        return mBiddingResultRepository.findById(id)
            .map(mBiddingResultMapper::toDto);
    }

    /**
     * Delete the mBiddingResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingResult : {}", id);
        mBiddingResultRepository.deleteById(id);
    }
}
