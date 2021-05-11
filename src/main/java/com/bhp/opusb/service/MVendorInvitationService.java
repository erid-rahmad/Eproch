package com.bhp.opusb.service;

import com.bhp.opusb.domain.MVendorInvitation;
import com.bhp.opusb.repository.MVendorInvitationRepository;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MVendorInvitationDTO;
import com.bhp.opusb.service.mapper.MVendorInvitationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MVendorInvitation}.
 */
@Service
@Transactional
public class MVendorInvitationService {

    private final Logger log = LoggerFactory.getLogger(MVendorInvitationService.class);

    private final MVendorInvitationRepository mVendorInvitationRepository;

    private final MVendorInvitationMapper mVendorInvitationMapper;

    public MVendorInvitationService(MVendorInvitationRepository mVendorInvitationRepository, MVendorInvitationMapper mVendorInvitationMapper) {
        this.mVendorInvitationRepository = mVendorInvitationRepository;
        this.mVendorInvitationMapper = mVendorInvitationMapper;
    }

    /**
     * Save a mVendorInvitation.
     *
     * @param mVendorInvitationDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorInvitationDTO save(MVendorInvitationDTO mVendorInvitationDTO) {
        log.debug("Request to save MVendorInvitation : {}", mVendorInvitationDTO);
        MVendorInvitation mVendorInvitation = mVendorInvitationMapper.toEntity(mVendorInvitationDTO);
        mVendorInvitation = mVendorInvitationRepository.save(mVendorInvitation);
        return mVendorInvitationMapper.toDto(mVendorInvitation);
    }

    /**
     * Save a list of mVendorInvitations.
     *
     * @param mVendorInvitationDTOs the entities to save.
     * @return the persisted entities.
     */
    public List<MVendorInvitationDTO> saveAll(List<MVendorInvitationDTO> mVendorInvitationDTOs, MBiddingDTO mBiddingDTO) {
        log.debug("Request to save MVendorInvitations : {}", mVendorInvitationDTOs);

        mVendorInvitationDTOs.forEach(invitation -> {
            invitation.setBiddingId(mBiddingDTO.getId());
            invitation.setAdOrganizationId(mBiddingDTO.getAdOrganizationId());
        });

        List<MVendorInvitation> mVendorInvitations = mVendorInvitationMapper.toEntity(mVendorInvitationDTOs);
        return mVendorInvitationMapper.toDto(mVendorInvitationRepository.saveAll(mVendorInvitations));
    }

    /**
     * Get all the mVendorInvitations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorInvitationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorInvitations");
        return mVendorInvitationRepository.findAll(pageable)
            .map(mVendorInvitationMapper::toDto);
    }

    /**
     * Get one mVendorInvitation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorInvitationDTO> findOne(Long id) {
        log.debug("Request to get MVendorInvitation : {}", id);
        return mVendorInvitationRepository.findById(id)
            .map(mVendorInvitationMapper::toDto);
    }

    /**
     * Delete the mVendorInvitation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorInvitation : {}", id);
        mVendorInvitationRepository.deleteById(id);
    }

    /**
     * Delete the given mVendorInvitations.
     *
     * @param mVendorInvitationDTOs the given mVendorInvitations.
     */
    public void deleteAll(List<MVendorInvitationDTO> mVendorInvitationDTOs) {
        log.debug("Request to delete all MVendorInvitations. count : {}", mVendorInvitationDTOs.size());
        mVendorInvitationRepository.deleteAll(mVendorInvitationMapper.toEntity(mVendorInvitationDTOs));
    }
}
