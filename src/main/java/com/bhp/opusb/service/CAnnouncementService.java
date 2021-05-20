package com.bhp.opusb.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.CAnnouncement;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingInvitation;
import com.bhp.opusb.domain.MVendorSuggestion;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.CAnnouncementRepository;
import com.bhp.opusb.repository.MBiddingInvitationRepository;
import com.bhp.opusb.repository.MBiddingSubmissionRepository;
import com.bhp.opusb.repository.MVendorSuggestionRepository;
import com.bhp.opusb.service.dto.*;
import com.bhp.opusb.service.mapper.AdUserMapper;
import com.bhp.opusb.service.mapper.CAnnouncementMapper;
import com.bhp.opusb.service.mapper.MBiddingMapper;

import com.bhp.opusb.service.mapper.MVendorSuggestionMapper;
import com.bhp.opusb.util.MapperJSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CAnnouncement}.
 */
@Service
@Transactional
public class CAnnouncementService {

    private final Logger log = LoggerFactory.getLogger(CAnnouncementService.class);

    private final CAnnouncementRepository cAnnouncementRepository;

    private final MBiddingSubmissionRepository mBiddingSubmissionRepository;

    @Autowired
    private MVendorSuggestionRepository mVendorSuggestionRepository;

    @Autowired
    private AdUserRepository adUserRepository;

    @Value("${application.attachment.upload-dir}")
    private String UploadDir;

    private final MBiddingInvitationRepository mBiddingInvitationRepository;

    private final MailService mailService;

    private final CAnnouncementMapper cAnnouncementMapper;
    private final MBiddingMapper mBiddingMapper;
    private final AdUserMapper adUserMapper;

    public CAnnouncementService(CAnnouncementRepository cAnnouncementRepository,
            MBiddingSubmissionRepository mBiddingSubmissionRepository,
            MBiddingInvitationRepository mBiddingInvitationRepository, MailService mailService,
            CAnnouncementMapper cAnnouncementMapper, MBiddingMapper mBiddingMapper,
            AdUserMapper adUserMapper) {
        this.cAnnouncementRepository = cAnnouncementRepository;
        this.mBiddingSubmissionRepository = mBiddingSubmissionRepository;
        this.mBiddingInvitationRepository = mBiddingInvitationRepository;
        this.mailService = mailService;
        this.cAnnouncementMapper = cAnnouncementMapper;
        this.mBiddingMapper = mBiddingMapper;
        this.adUserMapper = adUserMapper;
    }
    @Autowired
    MVendorSuggestionMapper mVendorSuggestionMapper;

    /**
     * Save a cAnnouncement.
     *
     * @param cAnnouncementDTO the entity to save.
     * @return the persisted entity.
     */



    public CAnnouncementDTO save(CAnnouncementDTO cAnnouncementDTO) {
        log.debug("Request to save CAnnouncement : {}", cAnnouncementDTO);
        CAnnouncement cAnnouncement = cAnnouncementMapper.toEntity(cAnnouncementDTO);
        cAnnouncement = cAnnouncementRepository.save(cAnnouncement);

        return cAnnouncementMapper.toDto(cAnnouncement);
    }

    /**
     * This will publish the announcement document to the selected vendors.
     * @param cAnnouncementPublishDTO
     */
    public void publish(CAnnouncementPublishDTO cAnnouncementPublishDTO) {
        log.info("this in {}", MapperJSONUtil.prettyLog(cAnnouncementPublishDTO));

        CAnnouncement cAnnouncement = cAnnouncementMapper.toEntity(cAnnouncementPublishDTO.getAnnouncement());
        final MBiddingDTO mBiddingDTO = cAnnouncementPublishDTO.getBidding();
        final String content = cAnnouncement.getDescription();
        final List<AdUserDTO> users = cAnnouncementPublishDTO.getUsers();
        final List<MVendorSuggestionDTO> mvendor =  cAnnouncementPublishDTO.getVendor();


        MBidding mBidding = mBiddingMapper.toEntity(mBiddingDTO);
        log.info("this vendor {}",mvendor);

        String body = content.replace("#biddingTitle", mBiddingDTO.getName());

        for ( final MVendorSuggestionDTO mvendor_ : mvendor ){
            MVendorSuggestion mVendorSuggestion =mVendorSuggestionMapper.toEntity(mvendor_);
            MBiddingInvitation mBiddingInvitation = new MBiddingInvitation()
                .active(true)
                .adOrganization(cAnnouncement.getAdOrganization())
                .bidding(mBidding)
                .vendor(mVendorSuggestion.getVendor())
                .invitationStatus("U")
                .announcement(cAnnouncement);

            // Create the invitation record.
            mBiddingInvitationRepository.save(mBiddingInvitation);
        }


        for (final AdUserDTO user : users) {
            final AdUser adUser = adUserMapper.toEntity(user);
            body = body.replace("#vendorName", user.getcVendorName());
            mailService.sendMailWithAttachment(user.getEmail(), "Bidding Invitation", body, false, true,
                UploadDir+"/"+cAnnouncementPublishDTO.getAnnouncement().getAttachmentName());
        }
        // Update the announcement published date.
        cAnnouncement.setPublishDate(ZonedDateTime.now());
        cAnnouncementRepository.save(cAnnouncement);
    }

    public Map<String,Object> emailInvitation (Long id){
        ArrayList emaillist =new ArrayList();
        ArrayList vendorlist = new ArrayList();

        Map<String,Object> dataForAnnouncment = new HashMap<String,Object>();
        List<MVendorSuggestion> mVendorSuggestion = mVendorSuggestionRepository.findbyheaderid(id);
        log.info(String.valueOf(mVendorSuggestion));

        for (MVendorSuggestion mVendorSuggestion1 : mVendorSuggestion){
            log.info("this vendor id {}",mVendorSuggestion1.getVendor().getId().toString());
            Map<String, Object> vendorlistdata = new HashMap<String,Object>();
            vendorlistdata.put("name",mVendorSuggestion1.getVendor().getName());
            vendorlistdata.put("code",mVendorSuggestion1.getVendor().getCode());
            vendorlist.add(vendorlistdata);
            List<AdUser> adUsers =adUserRepository.findBycVendorId(mVendorSuggestion1.getVendor().getId());
            log.info("this add user {}",adUsers);
            for (AdUser adUser : adUsers){
                log.info("this email {} count {}",adUser.getUser().getEmail());
                Map<String, Object> emaillistdata = new HashMap<String,Object>();
                emaillistdata.put("adUserId",adUser.getId());
                emaillistdata.put("name",adUser.getUser().getFirstName()+" "+adUser.getUser().getLastName());
                emaillistdata.put("email",adUser.getUser().getEmail());
                emaillistdata.put("position",adUser.getPosition());
                emaillistdata.put("vendor",adUser.getCVendor().getName());
                emaillistdata.put("biddingName",mVendorSuggestion1.getBidding().getName());
                emaillist.add(emaillistdata);
            }
        }
        dataForAnnouncment.put("emaillist",emaillist);
        dataForAnnouncment.put("vendorlist",vendorlist);


        return dataForAnnouncment;
    }

    /**
     * Get all the cAnnouncements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CAnnouncementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CAnnouncements");
        return cAnnouncementRepository.findAll(pageable)
            .map(cAnnouncementMapper::toDto);
    }

    /**
     * Get one cAnnouncement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CAnnouncementDTO> findOne(Long id) {
        log.debug("Request to get CAnnouncement : {}", id);
        return cAnnouncementRepository.findById(id)
            .map(cAnnouncementMapper::toDto);
    }

    /**
     * Delete the cAnnouncement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CAnnouncement : {}", id);
        cAnnouncementRepository.deleteById(id);
    }
}
