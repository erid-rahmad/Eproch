package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MProposalPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MProposalPrice} and its DTO {@link MProposalPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingSubmissionMapper.class, ADOrganizationMapper.class, CAttachmentMapper.class})
public interface MProposalPriceMapper extends EntityMapper<MProposalPriceDTO, MProposalPrice> {

    @Mapping(source = "biddingSubmission.id", target = "biddingSubmissionId")
    @Mapping(source = "biddingSubmission.bidding.name", target = "biddingSubmissionName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.fileName", target = "attachmentName")
    @Mapping(source = "attachment.downloadUrl", target = "attachmentUrl")
    MProposalPriceDTO toDto(MProposalPrice mProposalPrice);

    @Mapping(source = "biddingSubmissionId", target = "biddingSubmission")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "attachmentId", target = "attachment")
    MProposalPrice toEntity(MProposalPriceDTO mProposalPriceDTO);

    default MProposalPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProposalPrice mProposalPrice = new MProposalPrice();
        mProposalPrice.setId(id);
        return mProposalPrice;
    }
}
