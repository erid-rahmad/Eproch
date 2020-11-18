package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CProduct;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.repository.MVerificationRepository;
import com.bhp.opusb.service.dto.MVerificationDTO;
import com.bhp.opusb.service.dto.VerificationDTO;
import com.bhp.opusb.service.mapper.MVerificationMapper;
import com.bhp.opusb.service.mapper.VerificationMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MVerification}.
 */
@Service
@Transactional
public class MVerificationService {

    private final Logger log = LoggerFactory.getLogger(MVerificationService.class);

    private final ADOrganization organization;
    private final CProduct product;
    private final MVerificationRepository mVerificationRepository;

    private final MVerificationMapper mVerificationMapper;
    private final VerificationMapper verificationMapper;
    private final MVerificationLineService mVerificationLineService;

    public MVerificationService(MVerificationRepository mVerificationRepository,
            MVerificationMapper mVerificationMapper, MVerificationLineService mVerificationLineService) {
        this.mVerificationRepository = mVerificationRepository;
        this.mVerificationMapper = mVerificationMapper;
        this.mVerificationLineService = mVerificationLineService;

        product = new CProduct();
        organization = new ADOrganization();
        organization.setId(1L);
        product.setId(456645L);
        verificationMapper = new VerificationMapper(organization);
    }

    public MVerification submitEVerification(VerificationDTO verificationDTO) {
        // Ensure verification has generated ID.
        MVerification verification = verificationMapper.toMVerification(verificationDTO.getForm());
        mVerificationRepository.save(verification);

        // Batch save verification line.
        if(verificationDTO.getRemove().size() == 0){
            mVerificationLineService.saveAll(verificationDTO.getLine(), verification, organization, product);
        }else{
            mVerificationLineService.removeAll(verificationDTO.getRemove(), verification, organization, product);
        }

        return verification;
    }

    /**
     * Save a mVerification.
     *
     * @param mVerificationDTO the entity to save.
     * @return the persisted entity.
     */
    public MVerificationDTO save(MVerificationDTO mVerificationDTO) {
        log.debug("Request to save MVerification : {}", mVerificationDTO);
        MVerification mVerification = mVerificationMapper.toEntity(mVerificationDTO);
        mVerification = mVerificationRepository.save(mVerification);
        return mVerificationMapper.toDto(mVerification);
    }

    /**
     * Get all the mVerifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVerificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVerifications");
        return mVerificationRepository.findAll(pageable)
            .map(mVerificationMapper::toDto);
    }

    /**
     * Get one mVerification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVerificationDTO> findOne(Long id) {
        log.debug("Request to get MVerification : {}", id);
        return mVerificationRepository.findById(id)
            .map(mVerificationMapper::toDto);
    }

    /**
     * Delete the mVerification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVerification : {}", id);
        mVerificationRepository.deleteById(id);
    }
}
