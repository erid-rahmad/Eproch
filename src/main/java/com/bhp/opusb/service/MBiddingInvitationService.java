package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingInvitation;
import com.bhp.opusb.repository.MBiddingInvitationRepository;
import com.bhp.opusb.service.dto.MBiddingInvitationDTO;
import com.bhp.opusb.service.mapper.MBiddingInvitationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingInvitation}.
 */
@Service
@Transactional
public class MBiddingInvitationService {

    private final Logger log = LoggerFactory.getLogger(MBiddingInvitationService.class);

    private final MBiddingInvitationRepository mBiddingInvitationRepository;

    private final MBiddingInvitationMapper mBiddingInvitationMapper;

    public MBiddingInvitationService(MBiddingInvitationRepository mBiddingInvitationRepository, MBiddingInvitationMapper mBiddingInvitationMapper) {
        this.mBiddingInvitationRepository = mBiddingInvitationRepository;
        this.mBiddingInvitationMapper = mBiddingInvitationMapper;
    }

    /**
     * Save a mBiddingInvitation.
     *
     * @param mBiddingInvitationDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingInvitationDTO save(MBiddingInvitationDTO mBiddingInvitationDTO) {
        log.debug("Request to save MBiddingInvitation : {}", mBiddingInvitationDTO);
        if (mBiddingInvitationDTO.getInvitationStatus().equals("Tidak Berminat") ||mBiddingInvitationDTO.getInvitationStatus().equals("Terdaftar") ){
            mBiddingInvitationDTO.setAnswerDate(ZonedDateTime.now());
            log.info("change time");
        }
        MBiddingInvitation mBiddingInvitation = mBiddingInvitationMapper.toEntity(mBiddingInvitationDTO);
        mBiddingInvitation = mBiddingInvitationRepository.save(mBiddingInvitation);
        return mBiddingInvitationMapper.toDto(mBiddingInvitation);
    }

    /**
     * Get all the mBiddingInvitations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingInvitationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingInvitations");
        return mBiddingInvitationRepository.findAll(pageable)
            .map(mBiddingInvitationMapper::toDto);
    }

    /**
     * Get one mBiddingInvitation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingInvitationDTO> findOne(Long id) {
        log.debug("Request to get MBiddingInvitation change : {}", id);
        Optional<MBiddingInvitationDTO> mBiddingInvitationDTO =mBiddingInvitationRepository.findById(id)
            .map(mBiddingInvitationMapper::toDto);
        String email=mBiddingInvitationDTO.get().getAnnouncementDescription();
        email=email.replace("#biddingTitle",mBiddingInvitationDTO.get().getBiddingName());
        email=email.replace("#vendorName",mBiddingInvitationDTO.get().getVendorName());
        mBiddingInvitationDTO.get().setAnnouncementDescription(email);
        return mBiddingInvitationDTO;
    }

    /**
     * Delete the mBiddingInvitation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingInvitation : {}", id);
        mBiddingInvitationRepository.deleteById(id);
    }
}
