package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingEvaluationTeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingEvaluationTeam} and its DTO {@link MBiddingEvaluationTeamDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingMapper.class, MPrequalificationInformationMapper.class})
public interface MBiddingEvaluationTeamMapper extends EntityMapper<MBiddingEvaluationTeamDTO, MBiddingEvaluationTeam> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "bidding.documentNo", target = "biddingNo")
    @Mapping(source = "bidding.biddingType.name", target = "biddingType")
    @Mapping(source = "bidding.eventType.name", target = "biddingEventType")
    @Mapping(source = "bidding.costCenter.name", target = "biddingCostCenter")
    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "prequalification.name", target = "prequalificationName")
    @Mapping(source = "prequalification.documentNo", target = "prequalificationNo")
    @Mapping(source = "prequalification.preqEventName", target = "prequalificationEventType")
    MBiddingEvaluationTeamDTO toDto(MBiddingEvaluationTeam mBiddingEvaluationTeam);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "prequalificationId", target = "prequalification")
    MBiddingEvaluationTeam toEntity(MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO);

    default MBiddingEvaluationTeam fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingEvaluationTeam mBiddingEvaluationTeam = new MBiddingEvaluationTeam();
        mBiddingEvaluationTeam.setId(id);
        return mBiddingEvaluationTeam;
    }
}
