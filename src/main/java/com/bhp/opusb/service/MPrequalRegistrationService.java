package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalRegistration;
import com.bhp.opusb.repository.MPrequalRegistrationRepository;
import com.bhp.opusb.service.dto.MPrequalRegistrationDTO;
import com.bhp.opusb.service.mapper.MPrequalRegistrationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MPrequalRegistration}.
 */
@Service
@Transactional
public class MPrequalRegistrationService {

    private final Logger log = LoggerFactory.getLogger(MPrequalRegistrationService.class);

    private final MPrequalRegistrationRepository mPrequalRegistrationRepository;

    private final MPrequalRegistrationMapper mPrequalRegistrationMapper;

    public MPrequalRegistrationService(MPrequalRegistrationRepository mPrequalRegistrationRepository, MPrequalRegistrationMapper mPrequalRegistrationMapper) {
        this.mPrequalRegistrationRepository = mPrequalRegistrationRepository;
        this.mPrequalRegistrationMapper = mPrequalRegistrationMapper;
    }

    /**
     * Save a mPrequalRegistration.
     *
     * @param mPrequalRegistrationDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalRegistrationDTO save(MPrequalRegistrationDTO mPrequalRegistrationDTO) {
        log.debug("Request to save MPrequalRegistration : {}", mPrequalRegistrationDTO);
        MPrequalRegistration mPrequalRegistration = mPrequalRegistrationMapper.toEntity(mPrequalRegistrationDTO);
        if(mPrequalRegistration.getReason()!=null) mPrequalRegistration.setAnswerDate(ZonedDateTime.now());
        mPrequalRegistration = mPrequalRegistrationRepository.save(mPrequalRegistration);
        return mPrequalRegistrationMapper.toDto(mPrequalRegistration);
    }

    /**
     * Get all the mPrequalRegistrations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalRegistrationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalRegistrations");
        return mPrequalRegistrationRepository.findAll(pageable)
            .map(mPrequalRegistrationMapper::toDto);
    }

    /**
     * Get one mPrequalRegistration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalRegistrationDTO> findOne(Long id) {
        log.debug("Request to get MPrequalRegistration : {}", id);
        Optional<MPrequalRegistrationDTO> mPreqReg = mPrequalRegistrationRepository.findById(id)
            .map(mPrequalRegistrationMapper::toDto);
        String email=mPreqReg.get().getAnnouncementDescription();
        email=email.replace("#prequalificationTitle",mPreqReg.get().getPrequalificationName());
        email=email.replace("#vendorName",mPreqReg.get().getVendorName());
        mPreqReg.get().setAnnouncementDescription(email);
        return mPreqReg;
    }

    /**
     * Delete the mPrequalRegistration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalRegistration : {}", id);
        mPrequalRegistrationRepository.deleteById(id);
    }
}
