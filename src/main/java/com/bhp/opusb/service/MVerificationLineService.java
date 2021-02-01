package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.domain.MVerificationLine;
import com.bhp.opusb.repository.MVerificationLineRepository;
import com.bhp.opusb.service.dto.MVerificationLineDTO;
import com.bhp.opusb.service.mapper.MVerificationLineMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MVerificationLine}.
 */
@Service
@Transactional
public class MVerificationLineService {

    private final Logger log = LoggerFactory.getLogger(MVerificationLineService.class);

    private final MVerificationLineRepository mVerificationLineRepository;
    private final MMatchPOService mMatchPOService;

    private final MVerificationLineMapper mVerificationLineMapper;

    public MVerificationLineService(MVerificationLineRepository mVerificationLineRepository,
            MMatchPOService mMatchPOService, MVerificationLineMapper mVerificationLineMapper) {
        this.mVerificationLineRepository = mVerificationLineRepository;
        this.mMatchPOService = mMatchPOService;
        this.mVerificationLineMapper = mVerificationLineMapper;
    }

    /**
     * Save a mVerificationLine.
     *
     * @param mVerificationLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MVerificationLineDTO save(MVerificationLineDTO mVerificationLineDTO) {
        log.debug("Request to save MVerificationLine : {}", mVerificationLineDTO);
        MVerificationLine mVerificationLine = mVerificationLineMapper.toEntity(mVerificationLineDTO);
        mVerificationLine = mVerificationLineRepository.save(mVerificationLine);
        return mVerificationLineMapper.toDto(mVerificationLine);
    }

    public List<MVerificationLineDTO> saveAll(List<MVerificationLineDTO> mVerificationLineDTOs, MVerification mVerification, ADOrganization organization) {
        List<MVerificationLine> verificationLines = mVerificationLineMapper.toEntity(mVerificationLineDTOs);
        verificationLines.forEach(line ->
            line.active(true)
                .adOrganization(organization)
                .verification(mVerification)
        );

        return mVerificationLineMapper.toDto(mVerificationLineRepository.saveAll(verificationLines));
    }

    public void removeAll(List<MVerificationLineDTO> mVerificationLineDTOs) {
        mVerificationLineRepository.deleteAll(mVerificationLineMapper.toEntity(mVerificationLineDTOs));
        mVerificationLineDTOs.forEach(
            line -> mMatchPOService.openMatchPO(line.getAdOrganizationCode(), line.getcDocType(), line.getPoNo(),
                    line.getReceiveNo(), line.getLineNoPo(), line.getLineNoMr(), line.getOrderSuffix())
        );
    }

    /**
     * Get all the mVerificationLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVerificationLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVerificationLines");
        return mVerificationLineRepository.findAll(pageable)
            .map(mVerificationLineMapper::toDto);
    }

    /**
     * Get one mVerificationLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVerificationLineDTO> findOne(Long id) {
        log.debug("Request to get MVerificationLine : {}", id);
        return mVerificationLineRepository.findById(id)
            .map(mVerificationLineMapper::toDto);
    }

    /**
     * Delete the mVerificationLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVerificationLine : {}", id);
        mVerificationLineRepository.deleteById(id);
    }
}
