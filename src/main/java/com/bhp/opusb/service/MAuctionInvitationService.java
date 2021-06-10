package com.bhp.opusb.service;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.MAuctionInvitation;
import com.bhp.opusb.repository.MAuctionInvitationRepository;
import com.bhp.opusb.service.dto.MAuctionInvitationDTO;
import com.bhp.opusb.service.mapper.MAuctionInvitationMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MAuctionInvitation}.
 */
@Service
@Transactional
public class MAuctionInvitationService {

    private final Logger log = LoggerFactory.getLogger(MAuctionInvitationService.class);

    private final MAuctionInvitationRepository mAuctionInvitationRepository;

    private final MAuctionInvitationMapper mAuctionInvitationMapper;

    private final Document document;

    public MAuctionInvitationService(ApplicationProperties applicationProperties, MAuctionInvitationRepository mAuctionInvitationRepository, MAuctionInvitationMapper mAuctionInvitationMapper) {
        this.mAuctionInvitationRepository = mAuctionInvitationRepository;
        this.mAuctionInvitationMapper = mAuctionInvitationMapper;
        this.document = applicationProperties.getDocuments().get("auctionInvitation");
    }

    /**
     * Save a mAuctionInvitation.
     *
     * @param mAuctionInvitationDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionInvitationDTO save(MAuctionInvitationDTO mAuctionInvitationDTO) {
        log.debug("Request to save MAuctionInvitation : {}", mAuctionInvitationDTO);
        MAuctionInvitation mAuctionInvitation = mAuctionInvitationMapper.toEntity(mAuctionInvitationDTO);

        if (mAuctionInvitation.getDocumentNo() == null) {
            mAuctionInvitation.setDocumentNo(DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mAuctionInvitationRepository));
        }

        mAuctionInvitation = mAuctionInvitationRepository.save(mAuctionInvitation);
        return mAuctionInvitationMapper.toDto(mAuctionInvitation);
    }

    /**
     * Get all the mAuctionInvitations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionInvitationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctionInvitations");
        return mAuctionInvitationRepository.findAll(pageable)
            .map(mAuctionInvitationMapper::toDto);
    }

    /**
     * Get one mAuctionInvitation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionInvitationDTO> findOne(Long id) {
        log.debug("Request to get MAuctionInvitation : {}", id);
        return mAuctionInvitationRepository.findById(id)
            .map(mAuctionInvitationMapper::toDto);
    }

    /**
     * Delete the mAuctionInvitation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuctionInvitation : {}", id);
        mAuctionInvitationRepository.deleteById(id);
    }
}
