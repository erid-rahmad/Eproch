package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalificationInvitation;
import com.bhp.opusb.repository.MPrequalificationInvitationRepository;
import com.bhp.opusb.service.dto.MPrequalificationInformationDTO;
import com.bhp.opusb.service.dto.MPrequalificationInvitationDTO;
import com.bhp.opusb.service.mapper.MPrequalificationInvitationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MPrequalificationInvitation}.
 */
@Service
@Transactional
public class MPrequalificationInvitationService {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationInvitationService.class);

    private final MPrequalificationInvitationRepository mPrequalificationInvitationRepository;

    private final MPrequalificationInvitationMapper mPrequalificationInvitationMapper;

    public MPrequalificationInvitationService(MPrequalificationInvitationRepository mPrequalificationInvitationRepository, MPrequalificationInvitationMapper mPrequalificationInvitationMapper) {
        this.mPrequalificationInvitationRepository = mPrequalificationInvitationRepository;
        this.mPrequalificationInvitationMapper = mPrequalificationInvitationMapper;
    }

    /**
     * Save a mPrequalificationInvitation.
     *
     * @param mPrequalificationInvitationDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalificationInvitationDTO save(MPrequalificationInvitationDTO mPrequalificationInvitationDTO) {
        log.debug("Request to save MPrequalificationInvitation : {}", mPrequalificationInvitationDTO);
        MPrequalificationInvitation mPrequalificationInvitation = mPrequalificationInvitationMapper.toEntity(mPrequalificationInvitationDTO);
        mPrequalificationInvitation = mPrequalificationInvitationRepository.save(mPrequalificationInvitation);
        return mPrequalificationInvitationMapper.toDto(mPrequalificationInvitation);
    }

    /**
     * Save a list of mVendorInvitations.
     *
     * @param mVendorInvitationDTOs the entities to save.
     * @return the persisted entities.
     */
    public List<MPrequalificationInvitationDTO> saveAll(List<MPrequalificationInvitationDTO> mVendorInvitationDTOs, 
        MPrequalificationInformationDTO mPreqInfoDTO) {
        log.debug("Request to save MPrequalificationInvitations : {}", mVendorInvitationDTOs);

        mVendorInvitationDTOs.forEach(invitation -> {
            invitation.setPrequalificationId(mPreqInfoDTO.getId());
            invitation.setAdOrganizationId(mPreqInfoDTO.getAdOrganizationId());
        });

        List<MPrequalificationInvitation> mVendorInvitations = mPrequalificationInvitationMapper.toEntity(mVendorInvitationDTOs);
        return mPrequalificationInvitationMapper.toDto(mPrequalificationInvitationRepository.saveAll(mVendorInvitations));
    }

    /**
     * Get all the mPrequalificationInvitations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationInvitationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalificationInvitations");
        return mPrequalificationInvitationRepository.findAll(pageable)
            .map(mPrequalificationInvitationMapper::toDto);
    }

    /**
     * Get one mPrequalificationInvitation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalificationInvitationDTO> findOne(Long id) {
        log.debug("Request to get MPrequalificationInvitation : {}", id);
        return mPrequalificationInvitationRepository.findById(id)
            .map(mPrequalificationInvitationMapper::toDto);
    }

    /**
     * Delete the mPrequalificationInvitation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalificationInvitation : {}", id);
        mPrequalificationInvitationRepository.deleteById(id);
    }

    /**
     * Delete the given mVendorInvitations.
     *
     * @param mVendorInvitationDTOs the given mVendorInvitations.
     */
    public void deleteAll(List<MPrequalificationInvitationDTO> mVendorInvitationDTOs) {
        log.debug("Request to delete all MVendorInvitations. count : {}", mVendorInvitationDTOs.size());
        mPrequalificationInvitationRepository.deleteAll(mPrequalificationInvitationMapper.toEntity(mVendorInvitationDTOs));
    }
}
