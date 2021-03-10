package com.bhp.opusb.service;

import com.bhp.opusb.domain.*;
import com.bhp.opusb.repository.*;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.mapper.MBiddingMapper;
import com.bhp.opusb.util.MapperJSONUtil;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Service Implementation for managing {@link MBidding}.
 */
@Service
@Transactional
public class MBiddingService {

    private final Logger log = LoggerFactory.getLogger(MBiddingService.class);

    private final MBiddingRepository mBiddingRepository;

    private final MBiddingMapper mBiddingMapper;

    private final JavaMailSender javaMailSender;

    private final JHipsterProperties jHipsterProperties;

    @Autowired
    MailService mailService;

    @Autowired
    MBiddingLineService mBiddingLineService;

    @Autowired
    MProjectInformationService mProjectInformationService;

    @Autowired
    MBiddingLineRepository mBiddingLineRepository;

    @Autowired
    MProjectInformationRepository mProjectInformationRepository;

    @Autowired
    MVendorSuggestionRepository mVendorSuggestionRepository;

    @Autowired
    AdUserRepository adUserRepository;

    public MBiddingService(MBiddingRepository mBiddingRepository, MBiddingMapper mBiddingMapper, JavaMailSender javaMailSender, JHipsterProperties jHipsterProperties) {
        this.mBiddingRepository = mBiddingRepository;
        this.mBiddingMapper = mBiddingMapper;
        this.javaMailSender = javaMailSender;
        this.jHipsterProperties = jHipsterProperties;
    }

    /**
     * Save a mBidding.
     *
     * @param mBiddingDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingDTO save(MBiddingDTO mBiddingDTO) {
        log.debug("Request to save MBidding : {}", mBiddingDTO);

        if (mBiddingDTO.getBiddingNo()==null) {
            Random rnd = new Random();
            int number = rnd.nextInt(999999);
            String documentno = "BD-" + number;
            mBiddingDTO.setBiddingNo(documentno);
        }

        if (mBiddingDTO.getApproved()==true){
            log.info("send email to vendor");
//            mailService.sendEmail("erid.rahmad@gmail.com","testing","testing",false,false);
//            List<MVendorSuggestion> mVendorSuggestion = mVendorSuggestionRepository.findbyheaderid(664801);
            List<MVendorSuggestion> mVendorSuggestion = mVendorSuggestionRepository.findbyheaderid(mBiddingDTO.getId());

            for (MVendorSuggestion mVendorSuggestion1 : mVendorSuggestion){
                log.info("this vendor id {}",mVendorSuggestion1.getVendor().getId().toString());

                List<AdUser> adUsers =adUserRepository.findBycVendorId(mVendorSuggestion1.getVendor().getId());
                log.info("this add user {}",adUsers);

                for (AdUser adUser : adUsers){
                    log.info("this email {} count {}",adUser.getUser().getEmail());
                    mailService.sendEmail(adUser.getUser().getEmail(),"testing","testing",false,false);
                }
            }
            log.info("this vendor sugestion {}",mVendorSuggestion.toString());
        }

        MBidding mBidding = mBiddingMapper.toEntity(mBiddingDTO);
        mBidding = mBiddingRepository.save(mBidding);
        for (MBiddingLine mBiddingLine : mBiddingDTO.getBiddingLineList()){
            mBiddingLine.setBidding(mBidding);
            mBiddingLineRepository.save(mBiddingLine);
        }

        for (MProjectInformation mProjectInformation : mBiddingDTO.getProjectInformationList() ){
            mProjectInformation.setBidding(mBidding);
            mProjectInformationRepository.save(mProjectInformation);
        }

        return mBiddingMapper.toDto(mBidding);
    }



    /**
     * Get all the mBiddings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddings");
        return mBiddingRepository.findAll(pageable)
            .map(mBiddingMapper::toDto);
    }



    /**
     * Get one mBidding by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingDTO> findOne(Long id) {
        Optional<MBiddingDTO> mBiddingDTO = mBiddingRepository.findById(id)
            .map(mBiddingMapper::toDto);
        mBiddingDTO.get().setBiddingLineList(mBiddingLineService.findbyheader(mBiddingDTO.get().getId()));
        mBiddingDTO.get().setProjectInformationList(mProjectInformationService.findByBindId(mBiddingDTO.get().getId()));
        return mBiddingDTO;
    }

    /**
     * Delete the mBidding by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBidding : {}", id);
        mBiddingRepository.deleteById(id);
    }
}
