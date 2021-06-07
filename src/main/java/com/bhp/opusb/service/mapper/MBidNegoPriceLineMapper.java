package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBidNegoPriceLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBidNegoPriceLine} and its DTO {@link MBidNegoPriceLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBidNegoPriceMapper.class, MBiddingLineMapper.class, MProposalPriceLineMapper.class})
public interface MBidNegoPriceLineMapper extends EntityMapper<MBidNegoPriceLineDTO, MBidNegoPriceLine> {

    @Mapping(source = "bidNegoPrice.id", target = "bidNegoPriceId")
    @Mapping(source = "biddingLine.id", target = "biddingLineId")
    @Mapping(source = "biddingLine.quantity", target = "quantity")
    @Mapping(source = "biddingLine.uom.name", target = "uomName")
    @Mapping(source = "biddingLine.product.name", target = "productName")
    @Mapping(source = "biddingLine.uom.id", target = "uomId")
    @Mapping(source = "biddingLine.product.id", target = "productId")
    @Mapping(source = "biddingLine.ceilingPrice", target = "ceilingPrice")
    @Mapping(source = "biddingLine.totalCeilingPrice", target = "totalCeilingPrice")
    @Mapping(source = "proposalLine.id", target = "proposalLineId")
    @Mapping(source = "proposalLine.proposedPrice", target = "proposedPrice")
    @Mapping(source = "proposalLine.totalPriceSubmission", target = "totalPriceSubmission")
    MBidNegoPriceLineDTO toDto(MBidNegoPriceLine mBidNegoPriceLine);

    @Mapping(source = "bidNegoPriceId", target = "bidNegoPrice")
    @Mapping(source = "biddingLineId", target = "biddingLine")
    @Mapping(source = "proposalLineId", target = "proposalLine")
    MBidNegoPriceLine toEntity(MBidNegoPriceLineDTO mBidNegoPriceLineDTO);

    default MBidNegoPriceLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBidNegoPriceLine mBidNegoPriceLine = new MBidNegoPriceLine();
        mBidNegoPriceLine.setId(id);
        return mBidNegoPriceLine;
    }
}
