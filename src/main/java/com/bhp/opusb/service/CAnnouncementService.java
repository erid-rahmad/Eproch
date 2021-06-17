package com.bhp.opusb.service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.CAnnouncement;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingInvitation;
import com.bhp.opusb.repository.CAnnouncementRepository;
import com.bhp.opusb.repository.MBiddingInvitationRepository;
import com.bhp.opusb.service.dto.AdUserDTO;
import com.bhp.opusb.service.dto.CAnnouncementDTO;
import com.bhp.opusb.service.dto.CAnnouncementPublishDTO;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.mapper.AdUserMapper;
import com.bhp.opusb.service.mapper.CAnnouncementMapper;
import com.bhp.opusb.service.mapper.MBiddingMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final MBiddingInvitationRepository mBiddingInvitationRepository;

    private final MailService mailService;

    private final CAnnouncementMapper cAnnouncementMapper;
    private final MBiddingMapper mBiddingMapper;
    private final AdUserMapper adUserMapper;

    private final ApplicationProperties properties;

    public CAnnouncementService(CAnnouncementRepository cAnnouncementRepository,
            MBiddingInvitationRepository mBiddingInvitationRepository, MailService mailService,
            CAnnouncementMapper cAnnouncementMapper, MBiddingMapper mBiddingMapper, AdUserMapper adUserMapper,
            ApplicationProperties properties) {
        this.cAnnouncementRepository = cAnnouncementRepository;
        this.mBiddingInvitationRepository = mBiddingInvitationRepository;
        this.mailService = mailService;
        this.cAnnouncementMapper = cAnnouncementMapper;
        this.mBiddingMapper = mBiddingMapper;
        this.adUserMapper = adUserMapper;
        this.properties = properties;
    }

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
        CAnnouncement cAnnouncement = cAnnouncementMapper.toEntity(cAnnouncementPublishDTO.getAnnouncement());
        final MBiddingDTO mBiddingDTO = cAnnouncementPublishDTO.getBidding();
        final List<AdUserDTO> users = cAnnouncementPublishDTO.getUsers();
        final MBidding mBidding = mBiddingMapper.toEntity(mBiddingDTO);

        final String fileName = cAnnouncementPublishDTO.getAnnouncement().getAttachmentName();
        String content = cAnnouncement.getDescription();
        String attachmentPath = null;
        Set<Long> vendorIds = new HashSet<>();

        content = content.replace("#biddingTitle", mBiddingDTO.getName());

        if (fileName != null) {
            attachmentPath = properties.getAttachment().getUploadDir() + "/" + fileName;
        }

        for (final AdUserDTO user : users) {
            final Long vendorId = user.getcVendorId();

            // Create the invitation record per CVendor.
            if ( ! vendorIds.contains(vendorId)) {
                final AdUser adUser = adUserMapper.toEntity(user);
                MBiddingInvitation mBiddingInvitation = new MBiddingInvitation()
                    .active(true)
                    .adOrganization(cAnnouncement.getAdOrganization())
                    .bidding(mBidding)
                    .vendor(adUser.getCVendor())
                    .invitationStatus("U")
                    .announcement(cAnnouncement);

                mBiddingInvitationRepository.save(mBiddingInvitation);
                vendorIds.add(vendorId);
            }

            // Send the email.
            // TODO Don't send to user that has been already invited.
            String body = content.replace("#vendorName", user.getcVendorName());

            mailService.sendMailWithAttachment(user.getEmail(), "Bidding Invitation", body, false, true,
                attachmentPath);
        }

        // Update the announcement published date.
        cAnnouncement.setPublishDate(ZonedDateTime.now());
        cAnnouncementRepository.save(cAnnouncement);
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
