package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingNegotiationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingNegotiation} and its DTO {@link MBiddingNegotiationDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingEvalResultMapper.class, ADOrganizationMapper.class, MBiddingScheduleMapper.class})
public interface MBiddingNegotiationMapper extends EntityMapper<MBiddingNegotiationDTO, MBiddingNegotiation> {

    @Mapping(source = "biddingEvalResult.id", target = "biddingEvalResultId")
    @Mapping(source = "biddingEvalResult.biddingSubmission.bidding.id", target = "biddingId")
    @Mapping(source = "biddingEvalResult.biddingSubmission.bidding.documentNo", target = "biddingNo")
    @Mapping(source = "biddingEvalResult.biddingSubmission.bidding.name", target = "biddingTitle")
    @Mapping(source = "biddingEvalResult.evaluationStatus", target = "evaluationStatus")
    @Mapping(source = "biddingEvalResult.biddingSubmission.bidding.biddingStatus", target = "biddingStatus")
    @Mapping(source = "biddingEvalResult.biddingSubmission.bidding.biddingType.name", target = "biddingType")
    @Mapping(source = "biddingEvalResult.biddingSubmission.bidding.eventType.name", target = "eventType")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "biddingSchedule.id", target = "biddingScheduleId")
    MBiddingNegotiationDTO toDto(MBiddingNegotiation mBiddingNegotiation);

    @Mapping(source = "biddingEvalResultId", target = "biddingEvalResult")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingScheduleId", target = "biddingSchedule")
    MBiddingNegotiation toEntity(MBiddingNegotiationDTO mBiddingNegotiationDTO);

    default MBiddingNegotiation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingNegotiation mBiddingNegotiation = new MBiddingNegotiation();
        mBiddingNegotiation.setId(id);
        return mBiddingNegotiation;
    }
}
