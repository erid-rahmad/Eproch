package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBidNegoPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBidNegoPrice} and its DTO {@link MBidNegoPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, MVendorInvitationMapper.class, MProposalPriceMapper.class, MBiddingNegotiationLineMapper.class})
public interface MBidNegoPriceMapper extends EntityMapper<MBidNegoPriceDTO, MBidNegoPrice> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "vendorInvitation.id", target = "vendorInvitationId")
    @Mapping(source = "priceProposal.id", target = "priceProposalId")
    @Mapping(source = "negotiationLine.id", target = "negotiationLineId")
    MBidNegoPriceDTO toDto(MBidNegoPrice mBidNegoPrice);

    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "vendorInvitationId", target = "vendorInvitation")
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
