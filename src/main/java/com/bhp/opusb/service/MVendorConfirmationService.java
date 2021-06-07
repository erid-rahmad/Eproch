package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MVendorConfirmation;
import com.bhp.opusb.domain.MVendorConfirmationLine;
import com.bhp.opusb.repository.MBiddingRepository;
import com.bhp.opusb.repository.MVendorConfirmationLineRepository;
import com.bhp.opusb.repository.MVendorConfirmationRepository;
import com.bhp.opusb.service.dto.MBiddingNegotiationDTO;
import com.bhp.opusb.service.dto.MBiddingNegotiationLineDTO;
import com.bhp.opusb.service.dto.MVendorConfirmationDTO;
import com.bhp.opusb.service.dto.MVendorConfirmationLineDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationLineMapper;
import com.bhp.opusb.service.mapper.MVendorConfirmationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Implementation for managing {@link MVendorConfirmation}.
 */
@Service
@Transactional
public class MVendorConfirmationService {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationService.class);

    private final MVendorConfirmationRepository mVendorConfirmationRepository;
    private final MVendorConfirmationLineRepository mVendorConfirmationLineRepository;
    private final MBiddingRepository mBiddingRepository;

    private final MVendorConfirmationMapper mVendorConfirmationMapper;
    private final MVendorConfirmationLineMapper mVendorConfirmationLineMapper;

    public MVendorConfirmationService(MVendorConfirmationRepository mVendorConfirmationRepository, 
    MVendorConfirmationMapper mVendorConfirmationMapper, MBiddingRepository mBiddingRepository,
    MVendorConfirmationLineRepository mVendorConfirmationLineRepository,
    MVendorConfirmationLineMapper mVendorConfirmationLineMapper) {
        this.mVendorConfirmationRepository = mVendorConfirmationRepository;
        this.mVendorConfirmationMapper = mVendorConfirmationMapper;
        this.mVendorConfirmationLineRepository = mVendorConfirmationLineRepository;
        this.mBiddingRepository = mBiddingRepository;
        this.mVendorConfirmationLineMapper = mVendorConfirmationLineMapper;
    }

    /**
     * Save a mVendorConfirmation.
     *
     * @param mVendorConfirmationDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorConfirmationDTO save(MVendorConfirmationDTO mVendorConfirmationDTO) {
        log.debug("Request to save MVendorConfirmation : {}", mVendorConfirmationDTO);
        MVendorConfirmation mVendorConfirmation = mVendorConfirmationMapper.toEntity(mVendorConfirmationDTO);
        mVendorConfirmation = mVendorConfirmationRepository.save(mVendorConfirmation);
        return mVendorConfirmationMapper.toDto(mVendorConfirmation);
    }

    /**
     * Get all the mVendorConfirmations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorConfirmationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorConfirmations");
        return mVendorConfirmationRepository.findAll(pageable)
            .map(mVendorConfirmationMapper::toDto);
    }

    /**
     * Get one mVendorConfirmation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorConfirmationDTO> findOne(Long id) {
        log.debug("Request to get MVendorConfirmation : {}", id);
        return mVendorConfirmationRepository.findById(id)
            .map(mVendorConfirmationMapper::toDto);
    }

    /**
     * Delete the mVendorConfirmation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorConfirmation : {}", id);
        mVendorConfirmationRepository.deleteById(id);
    }

    public MVendorConfirmationDTO generateConfirmation(@Valid MBiddingNegotiationDTO mBiddingNegotiationDTO) {
        MVendorConfirmationDTO dto = new MVendorConfirmationDTO();

        dto.setBiddingId(mBiddingNegotiationDTO.getBiddingId());
        dto.setAdOrganizationId(mBiddingNegotiationDTO.getAdOrganizationId());
        MBidding mb = mBiddingRepository.findById(dto.getBiddingId()).get();
        dto.setCurrencyId(mb.getCurrency().getId());
        dto.setCostCenterId(mb.getCostCenter().getId());
        dto.setPicId(mb.getAdUser().getId());
        dto.setActive(true);

        MVendorConfirmation result = mVendorConfirmationRepository.save(mVendorConfirmationMapper.toEntity(dto));

        List<MVendorConfirmationLine> vcls = new ArrayList<>();
        for(MBiddingNegotiationLineDTO line: mBiddingNegotiationDTO.getLine()){
            MVendorConfirmationLineDTO vcldto = new MVendorConfirmationLineDTO();
            vcldto.setActive(true);
            vcldto.setAdOrganizationId(line.getAdOrganizationId());
            vcldto.setVendorId(line.getVendorId());
            vcldto.setBiddingEvalResultId(line.getBiddingEvalResultId());
            vcldto.setVendorConfirmationId(result.getId());
            vcls.add(mVendorConfirmationLineMapper.toEntity(vcldto));
        }
        mVendorConfirmationLineRepository.saveAll(vcls);
        return mVendorConfirmationMapper.toDto(result);
    }
}
