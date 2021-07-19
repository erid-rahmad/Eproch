package com.bhp.opusb.service;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.domain.MPrequalAnnouncement;
import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.repository.MPrequalAnnouncementRepository;
import com.bhp.opusb.service.dto.AdUserDTO;
import com.bhp.opusb.service.dto.MPrequalAnnouncementDTO;
import com.bhp.opusb.service.dto.MPrequalAnnouncementPublishDTO;
import com.bhp.opusb.service.dto.MPrequalificationInformationDTO;
import com.bhp.opusb.service.mapper.MPrequalAnnouncementMapper;
import com.bhp.opusb.service.mapper.MPrequalificationInformationMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link MPrequalAnnouncement}.
 */
@Service
@Transactional
public class MPrequalAnnouncementService {

    private final Logger log = LoggerFactory.getLogger(MPrequalAnnouncementService.class);

    private final MPrequalAnnouncementRepository mPrequalAnnouncementRepository;

    private final MPrequalAnnouncementMapper mPrequalAnnouncementMapper;
    private final MPrequalificationInformationMapper mPrequalificationInformationMapper;
    
    private final ApplicationProperties properties;
    
    private final MailService mailService;

    public MPrequalAnnouncementService(MPrequalAnnouncementRepository mPrequalAnnouncementRepository, 
        MPrequalAnnouncementMapper mPrequalAnnouncementMapper, 
        MPrequalificationInformationMapper mPrequalificationInformationMapper,
        ApplicationProperties properties, MailService mailService) {
        this.mPrequalAnnouncementRepository = mPrequalAnnouncementRepository;
        this.mPrequalAnnouncementMapper = mPrequalAnnouncementMapper;
        this.mPrequalificationInformationMapper = mPrequalificationInformationMapper;
        this.properties = properties;
        this.mailService = mailService;
    }

    /**
     * Save a mPrequalAnnouncement.
     *
     * @param mPrequalAnnouncementDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalAnnouncementDTO save(MPrequalAnnouncementDTO mPrequalAnnouncementDTO) {
        log.debug("Request to save MPrequalAnnouncement : {}", mPrequalAnnouncementDTO);
        MPrequalAnnouncement mPrequalAnnouncement = mPrequalAnnouncementMapper.toEntity(mPrequalAnnouncementDTO);
        mPrequalAnnouncement = mPrequalAnnouncementRepository.save(mPrequalAnnouncement);
        return mPrequalAnnouncementMapper.toDto(mPrequalAnnouncement);
    }

    /**
     * Get all the mPrequalAnnouncements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalAnnouncementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalAnnouncements");
        return mPrequalAnnouncementRepository.findAll(pageable)
            .map(mPrequalAnnouncementMapper::toDto);
    }

    /**
     * This will publish the announcement document to the selected vendors.
     * @param cAnnouncementPublishDTO
     */
    public void publish(MPrequalAnnouncementPublishDTO mPrequalAnnouncementPublishDTO) {
        MPrequalAnnouncement mPrequalAnnouncement = 
            mPrequalAnnouncementMapper.toEntity(mPrequalAnnouncementPublishDTO.getAnnouncement());
        final MPrequalificationInformationDTO mPreqInfoDTO = mPrequalAnnouncementPublishDTO.getPrequalification();
        final List<AdUserDTO> users = mPrequalAnnouncementPublishDTO.getUsers();
        final MPrequalificationInformation mPreqInfo = mPrequalificationInformationMapper.toEntity(mPreqInfoDTO);

        final String fileName = mPrequalAnnouncementPublishDTO.getAnnouncement().getAttachmentName();
        String content = mPrequalAnnouncement.getDescription();
        String attachmentPath = null;
        Set<Long> vendorIds = new HashSet<>();

        content = content.replace("#prequalificationTitle", mPreqInfoDTO.getName());

        if (fileName != null) {
            attachmentPath = properties.getAttachment().getUploadDir() + "/" + fileName;
        }

        for (final AdUserDTO user : users) {
            final Long vendorId = user.getcVendorId();

            // Create the invitation record per CVendor.
            /*
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
            */

            // Send the email.
            // TODO Don't send to user that has been already invited.
            String body = content.replace("#vendorName", user.getcVendorName());

            mailService.sendMailWithAttachment(user.getEmail(), "Prequalifiation Invitation", body, false, true,
                attachmentPath);
        }

        // Update the announcement published date.
        mPrequalAnnouncement.setPublishDate(ZonedDateTime.now());
        mPrequalAnnouncementRepository.save(mPrequalAnnouncement);
    }

    /**
     * Get one mPrequalAnnouncement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalAnnouncementDTO> findOne(Long id) {
        log.debug("Request to get MPrequalAnnouncement : {}", id);
        return mPrequalAnnouncementRepository.findById(id)
            .map(mPrequalAnnouncementMapper::toDto);
    }

    /**
     * Delete the mPrequalAnnouncement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalAnnouncement : {}", id);
        mPrequalAnnouncementRepository.deleteById(id);
    }
}
