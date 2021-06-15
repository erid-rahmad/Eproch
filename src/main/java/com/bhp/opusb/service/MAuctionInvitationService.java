package com.bhp.opusb.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.domain.MAuctionInvitation;
import com.bhp.opusb.repository.CDocumentTypeRepository;
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

/**
 * Service Implementation for managing {@link MAuctionInvitation}.
 */
@Service
@Transactional
public class MAuctionInvitationService {

    private final Logger log = LoggerFactory.getLogger(MAuctionInvitationService.class);

    private final CDocumentTypeRepository cDocumentTypeRepository;

    private final MAuctionInvitationRepository mAuctionInvitationRepository;

    private final MAuctionInvitationMapper mAuctionInvitationMapper;

    private final Document document;

    public MAuctionInvitationService(ApplicationProperties applicationProperties,
            CDocumentTypeRepository cDocumentTypeRepository, MAuctionInvitationRepository mAuctionInvitationRepository,
            MAuctionInvitationMapper mAuctionInvitationMapper) {
        this.cDocumentTypeRepository = cDocumentTypeRepository;
        this.mAuctionInvitationRepository = mAuctionInvitationRepository;
        this.mAuctionInvitationMapper = mAuctionInvitationMapper;
        this.document = applicationProperties.getDocuments().get("auctionInvitation");
    }

    public List<MAuctionInvitationDTO> create(MAuction mAuction, List<Long> vendorIds) {
        log.debug("Request to create MAuctionInvitation for Auction {} and the following vendors : {}", mAuction.getDocumentNo(), vendorIds);
        List<MAuctionInvitation> invitations = vendorIds.stream()
            .map(vendorId -> {
                CVendor cVendor = new CVendor();
                cVendor.setId(vendorId);

                final MAuctionInvitation mAuctionInvitation = new MAuctionInvitation()
                    .active(true)
                    .adOrganization(mAuction.getAdOrganization())
                    .auction(mAuction)
                    .dateTrx(ZonedDateTime.now())
                    .documentAction(DocumentUtil.STATUS_ACCEPT)
                    .documentNo(DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mAuctionInvitationRepository))
                    .documentStatus(DocumentUtil.STATUS_DRAFT)
                    .vendor(cVendor);

                cDocumentTypeRepository.findFirstByName(document.getDocumentType())
                    .ifPresent(mAuctionInvitation::setDocumentType);

                return mAuctionInvitation;
            })
            .collect(Collectors.toList());
    
        return mAuctionInvitationMapper.toDto(mAuctionInvitationRepository.saveAll(invitations));
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
            mAuctionInvitation.setDocumentNo(
                    DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mAuctionInvitationRepository));
        }

        final String documentAction = mAuctionInvitation.getDocumentAction();
        final boolean processed = Boolean.TRUE.equals(mAuctionInvitation.isProcessed());
        final boolean accept = DocumentUtil.isAccept(documentAction);

        // It is transitioned from draft.
        if ( ! processed && documentAction != null && DocumentUtil.isDraft(mAuctionInvitation.getDocumentStatus())) {
            mAuctionInvitation
                .approved(accept)
                .processed(true)
                .documentAction(null)
                .documentStatus(documentAction);
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
