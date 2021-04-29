package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.CAnnouncement;
import com.bhp.opusb.domain.MVendorSuggestion;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.CAnnouncementRepository;
import com.bhp.opusb.repository.MVendorSuggestionRepository;
import com.bhp.opusb.service.dto.CAnnouncementDTO;
import com.bhp.opusb.service.mapper.CAnnouncementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link CAnnouncement}.
 */
@Service
@Transactional
public class CAnnouncementService {

    private final Logger log = LoggerFactory.getLogger(CAnnouncementService.class);

    private final CAnnouncementRepository cAnnouncementRepository;

    private final CAnnouncementMapper cAnnouncementMapper;

    public CAnnouncementService(CAnnouncementRepository cAnnouncementRepository, CAnnouncementMapper cAnnouncementMapper) {
        this.cAnnouncementRepository = cAnnouncementRepository;
        this.cAnnouncementMapper = cAnnouncementMapper;
    }

    @Autowired
    MVendorSuggestionRepository mVendorSuggestionRepository;
    @Autowired
    AdUserRepository adUserRepository;
    @Autowired
    private MailService mailService;

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
        log.info("this cAnnouncement to get {}",cAnnouncement);

        try {
            for (Map<String,Object> emaillist_:cAnnouncementDTO.getEmaillist()) {
                String email = cAnnouncementDTO.getDescription();
                log.info(String.valueOf(emaillist_.get("email")));
                email=email.replace("#TenderName",emaillist_.get("biddingName").toString());
                email=email.replace("#VendorName",emaillist_.get("vendor").toString());
//            email=email.replace("#JenisPerusahaan","majumundur");
                log.info( "this email{}",email);
                mailService.sendEmail(emaillist_.get("email").toString(),"Bidding Invitation",email,false,true);
            }
        }catch (Exception e){}

        return cAnnouncementMapper.toDto(cAnnouncement);
    }

    public ArrayList emailInvitation (Long id){

        List<MVendorSuggestion> mVendorSuggestion = mVendorSuggestionRepository.findbyheaderid(id);
        log.info(String.valueOf(mVendorSuggestion));

        ArrayList emaillist =new ArrayList();
        for (MVendorSuggestion mVendorSuggestion1 : mVendorSuggestion){
            log.info("this vendor id {}",mVendorSuggestion1.getVendor().getId().toString());
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
        return emaillist;
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
