package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingNegotiationLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingNegotiationLine} and its DTO {@link MBiddingNegotiationLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingNegotiationMapper.class, MBiddingEvalResultMapper.class})
public interface MBiddingNegotiationLineMapper extends EntityMapper<MBiddingNegotiationLineDTO, MBiddingNegotiationLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "negotiation.id", target = "negotiationId")
    @Mapping(source = "biddingEvalResult.id", target = "biddingEvalResultId")
    @Mapping(source = "biddingEvalResult.status", target = "evalStatus")
    @Mapping(source = "biddingEvalResult.biddingSubmission.vendor.id", target = "vendorId")
    @Mapping(source = "biddingEvalResult.biddingSubmission.vendor.name", target = "vendorName")
    MBiddingNegotiationLineDTO toDto(MBiddingNegotiationLine mBiddingNegotiationLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "negotiationId", target = "negotiation")
    @Mapping(source = "biddingEvalResultId", target = "biddingEvalResult")
    MBiddingNegotiationLine toEntity(MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO);

    default MBiddingNegotiationLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingNegotiationLine mBiddingNegotiationLine = new MBiddingNegotiationLine();
        mBiddingNegotiationLine.setId(id);
        return mBiddingNegotiationLine;
    }
}
