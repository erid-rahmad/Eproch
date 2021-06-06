package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingNegotiationChatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingNegotiationChat} and its DTO {@link MBiddingNegotiationChatDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingNegotiationLineMapper.class, MBiddingMapper.class, CVendorMapper.class, CAttachmentMapper.class})
public interface MBiddingNegotiationChatMapper extends EntityMapper<MBiddingNegotiationChatDTO, MBiddingNegotiationChat> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "negotiationLine.id", target = "negotiationLineId")
    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.downloadUrl", target = "downloadUrl")
    MBiddingNegotiationChatDTO toDto(MBiddingNegotiationChat mBiddingNegotiationChat);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "negotiationLineId", target = "negotiationLine")
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "attachmentId", target = "attachment")
    MBiddingNegotiationChat toEntity(MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO);

    default MBiddingNegotiationChat fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingNegotiationChat mBiddingNegotiationChat = new MBiddingNegotiationChat();
        mBiddingNegotiationChat.setId(id);
        return mBiddingNegotiationChat;
    }
}
