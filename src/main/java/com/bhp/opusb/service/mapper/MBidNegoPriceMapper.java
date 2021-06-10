package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBidNegoPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBidNegoPrice} and its DTO {@link MBidNegoPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, MBiddingMapper.class, MProposalPriceMapper.class, MBiddingNegotiationLineMapper.class})
public interface MBidNegoPriceMapper extends EntityMapper<MBidNegoPriceDTO, MBidNegoPrice> {

    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.fileName", target = "fileName")
    @Mapping(source = "attachment.downloadUrl", target = "downloadUrl")
    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "priceProposal.id", target = "priceProposalId")
    @Mapping(source = "negotiationLine.id", target = "negotiationLineId")
    MBidNegoPriceDTO toDto(MBidNegoPrice mBidNegoPrice);

    @Mapping(source = "attachmentId", target = "attachment")
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "priceProposalId", target = "priceProposal")
    @Mapping(source = "negotiationLineId", target = "negotiationLine")
    MBidNegoPrice toEntity(MBidNegoPriceDTO mBidNegoPriceDTO);

    default MBidNegoPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBidNegoPrice mBidNegoPrice = new MBidNegoPrice();
        mBidNegoPrice.setId(id);
        return mBidNegoPrice;
    }
}
