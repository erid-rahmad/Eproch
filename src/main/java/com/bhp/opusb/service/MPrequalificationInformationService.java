package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalificationInformation;
import com.bhp.opusb.domain.enumeration.MPrequalificationProcess;
import com.bhp.opusb.repository.MPrequalificationInformationRepository;
import com.bhp.opusb.service.dto.MPrequalVendorSuggestionDTO;
import com.bhp.opusb.service.dto.MPrequalificationFormDTO;
import com.bhp.opusb.service.dto.MPrequalificationInformationDTO;
import com.bhp.opusb.service.dto.MPrequalificationInvitationDTO;
import com.bhp.opusb.service.dto.MPrequalificationScheduleDTO;
import com.bhp.opusb.service.mapper.MPrequalificationEventMapper;
import com.bhp.opusb.service.mapper.MPrequalificationFormMapper;
import com.bhp.opusb.service.mapper.MPrequalificationInformationMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MPrequalificationInformation}.
 */
@Service
@Transactional
public class MPrequalificationInformationService {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationInformationService.class);

    private final MPrequalificationInformationRepository mPrequalificationInformationRepository;

    private final MPrequalificationInformationMapper mPrequalificationInformationMapper;
    private final MPrequalificationFormMapper mPrequalificationFormMapper;
    private final MPrequalificationEventMapper mPrequalificationEventMapper;

    private final MPrequalificationInvitationService mPrequalificationInvitationService;
    private final MPrequalVendorSuggestionService mPrequalVendorSuggestionService;
    private final MPrequalificationEventService mPrequalificationEventService;
    private final MPrequalificationScheduleService mPrequalificationScheduleService;

    public MPrequalificationInformationService(MPrequalificationInformationRepository mPrequalificationInformationRepository, 
        MPrequalificationInvitationService mPrequalificationInvitationService, MPrequalVendorSuggestionService mPrequalVendorSuggestionService,
        MPrequalificationInformationMapper mPrequalificationInformationMapper, MPrequalificationFormMapper mPrequalificationFormMapper,
        MPrequalificationEventMapper mPrequalificationEventMapper, MPrequalificationScheduleService mPrequalificationScheduleService,
        MPrequalificationEventService mPrequalificationEventService) {
        this.mPrequalificationInformationRepository = mPrequalificationInformationRepository;
        this.mPrequalificationInvitationService = mPrequalificationInvitationService;
        this.mPrequalVendorSuggestionService = mPrequalVendorSuggestionService;
        this.mPrequalificationInformationMapper = mPrequalificationInformationMapper;
        this.mPrequalificationFormMapper = mPrequalificationFormMapper;
        this.mPrequalificationEventService = mPrequalificationEventService;
        this.mPrequalificationEventMapper = mPrequalificationEventMapper;
        this.mPrequalificationScheduleService = mPrequalificationScheduleService;
    }

    /**
     * Save a mPrequalificationInformation.
     *
     * @param mPrequalificationInformationDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalificationInformationDTO save(MPrequalificationInformationDTO mPrequalificationInformationDTO) {
        log.debug("Request to save MPrequalificationInformation : {}", mPrequalificationInformationDTO);
        MPrequalificationInformation mPrequalificationInformation = mPrequalificationInformationMapper.toEntity(mPrequalificationInformationDTO);
        mPrequalificationInformation = mPrequalificationInformationRepository.save(mPrequalificationInformation);
        return mPrequalificationInformationMapper.toDto(mPrequalificationInformation);
    }

    /**
     * Get all the mPrequalificationInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalificationInformations");
        return mPrequalificationInformationRepository.findAll(pageable)
            .map(mPrequalificationInformationMapper::toDto);
    }

    /**
     * Get one mPrequalificationInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalificationInformationDTO> findOne(Long id) {
        log.debug("Request to get MPrequalificationInformation : {}", id);
        return mPrequalificationInformationRepository.findById(id)
            .map(mPrequalificationInformationMapper::toDto);
    }

    /**
     * Delete the mPrequalificationInformation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalificationInformation : {}", id);
        mPrequalificationInformationRepository.deleteById(id);
    }

    @Transactional
    public MPrequalificationInformationDTO saveForm(MPrequalificationFormDTO mPrequalificationInformationDTO) {
        log.debug("Request to save MPrequalificationInformation : {}", mPrequalificationInformationDTO);
        MPrequalificationInformation preqInfo = mPrequalificationInformationMapper.toEntity(mPrequalificationInformationDTO);
        MPrequalificationProcess step = mPrequalificationInformationDTO.getStep();

        if(MPrequalificationProcess.INFO.equals(step)){
            if (preqInfo.getDocumentNo() == null) {
                preqInfo.setDocumentNo(DocumentUtil.buildDocumentNumber("PQ-", mPrequalificationInformationRepository));
             }
 
             // Set the default Purchase Order document type.
             /*
             if (preqInfo.getDocumentType() == null) {
                 cDocumentTypeRepository.findFirstByName(document.getDocumentType())
                     .ifPresent(mBidding::setDocumentType);
             }
             */
 
             preqInfo = mPrequalificationInformationRepository.save(preqInfo);
             mPrequalificationInformationDTO = mPrequalificationFormMapper.toDto(preqInfo);
        } else if (MPrequalificationProcess.INVITATION.equals(step)) {
            final List<MPrequalificationInvitationDTO> vendorInvitations = mPrequalificationInformationDTO.getVendorInvitations();
            final List<MPrequalificationInvitationDTO> removedVendorInvitations = mPrequalificationInformationDTO.getRemovedVendorInvitations();
            final List<MPrequalVendorSuggestionDTO> vendorSuggestions = mPrequalificationInformationDTO.getVendorSuggestions();
            final List<MPrequalVendorSuggestionDTO> removedVendorSuggestions = mPrequalificationInformationDTO.getRemovedVendorSuggestions();
            saveInvitation(mPrequalificationInformationDTO, vendorInvitations, vendorSuggestions, removedVendorInvitations, removedVendorSuggestions);
        } else if (MPrequalificationProcess.EVENT.equals(step)) {
            mPrequalificationInformationDTO.getEvent().setPrequalificationId(mPrequalificationInformationDTO.getId());
            mPrequalificationInformationDTO.getEvent().setAdOrganizationId(mPrequalificationInformationDTO.getAdOrganizationId());

            mPrequalificationInformationDTO.setEvent(mPrequalificationEventService.save(mPrequalificationInformationDTO.getEvent()));
            mPrequalificationScheduleService.initSchedules(mPrequalificationEventMapper.toEntity(mPrequalificationInformationDTO.getEvent()));
        } else {
            final List<MPrequalificationScheduleDTO> schedules = mPrequalificationInformationDTO.getPreqSchedules();
            saveSchedule(mPrequalificationInformationDTO, schedules);
        } 

        return mPrequalificationInformationDTO;
    }

    private MPrequalificationInformationDTO saveSchedule(MPrequalificationInformationDTO mPrequalificationInformationDTO, 
        List<MPrequalificationScheduleDTO> schedules) {
        mPrequalificationScheduleService.saveAll(schedules);
        return mPrequalificationInformationDTO;
    }

    /**
     * Save vendor invitation and suggestions in the second step.
     * @param mBiddingDTO
     * @param vendorInvitations
     * @param vendorSuggestions
     * @param removedVendorInvitations
     * @param removedVendorSuggestions
     * @return
     */
    private MPrequalificationInformationDTO saveInvitation(MPrequalificationInformationDTO mBiddingDTO, 
            List<MPrequalificationInvitationDTO> vendorInvitations, List<MPrequalVendorSuggestionDTO> vendorSuggestions, 
            List<MPrequalificationInvitationDTO> removedVendorInvitations, List<MPrequalVendorSuggestionDTO> removedVendorSuggestions) {
        mPrequalificationInvitationService.saveAll(vendorInvitations, mBiddingDTO);
        mPrequalificationInvitationService.deleteAll(removedVendorInvitations);
        mPrequalVendorSuggestionService.saveAll(vendorSuggestions, mBiddingDTO);
        mPrequalVendorSuggestionService.deleteAll(removedVendorSuggestions);
        removedVendorInvitations.clear();
        removedVendorSuggestions.clear();
        return mBiddingDTO;
    }

    @Transactional(readOnly = true)
    public Optional<MPrequalificationFormDTO> findOneForm(Long id, MPrequalificationProcess step) {
        log.debug("Request to get MBidding (FormDTO) : {}", id);
        Optional<MPrequalificationFormDTO> record = mPrequalificationInformationRepository.findById(id).map(mPrequalificationFormMapper::toDto);

        record.ifPresent(dto -> {
            dto.setStep(step);
        });

        return record;
    }
}
