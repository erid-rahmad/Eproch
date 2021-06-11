package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.repository.MAuctionRepository;
import com.bhp.opusb.service.dto.MAuctionDTO;
import com.bhp.opusb.service.dto.MAuctionParticipantDTO;
import com.bhp.opusb.service.mapper.MAuctionMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MAuction}.
 */
@Service
@Transactional
public class MAuctionService {

    private final Logger log = LoggerFactory.getLogger(MAuctionService.class);

    private final MAuctionInvitationService mAuctionInvitationService;
    private final MAuctionParticipantService mAuctionParticipantService;

    private final MAuctionRepository mAuctionRepository;

    private final MAuctionMapper mAuctionMapper;

    private final Document document;

    public MAuctionService(ApplicationProperties applicationProperties,
            MAuctionInvitationService mAuctionInvitationService, MAuctionParticipantService mAuctionParticipantService,
            MAuctionRepository mAuctionRepository, MAuctionMapper mAuctionMapper) {
        this.mAuctionInvitationService = mAuctionInvitationService;
        this.mAuctionParticipantService = mAuctionParticipantService;
        this.mAuctionRepository = mAuctionRepository;
        this.mAuctionMapper = mAuctionMapper;
        this.document = applicationProperties.getDocuments().get("auction");
    }

    /**
     * Save a mAuction.
     *
     * @param mAuctionDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionDTO save(MAuctionDTO mAuctionDTO) {
        log.debug("Request to save MAuction : {}", mAuctionDTO);
        MAuction mAuction = mAuctionMapper.toEntity(mAuctionDTO);

        if (mAuction.getDocumentNo() == null) {
            mAuction.setDocumentNo(DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mAuctionRepository));
        }
        
        boolean isPublishing = ! Boolean.TRUE.equals(mAuction.isProcessed()) && DocumentUtil.isPublish(mAuction.getDocumentStatus());

        if (isPublishing) {
            List<Long> vendorIds = mAuctionParticipantService.findByAuctionId(mAuction.getId())
                .stream()
                .map(MAuctionParticipantDTO::getVendorId)
                .collect(Collectors.toList());

            mAuctionInvitationService.create(mAuction, vendorIds);
            mAuction.setProcessed(true);
        }

        mAuction = mAuctionRepository.save(mAuction);
        return mAuctionMapper.toDto(mAuction);
    }

    /**
     * Get all the mAuctions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctions");
        return mAuctionRepository.findAll(pageable)
            .map(mAuctionMapper::toDto);
    }

    /**
     * Get one mAuction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionDTO> findOne(Long id) {
        log.debug("Request to get MAuction : {}", id);
        return mAuctionRepository.findById(id)
            .map(mAuctionMapper::toDto);
    }

    /**
     * Delete the mAuction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuction : {}", id);
        mAuctionRepository.deleteById(id);
    }
}
