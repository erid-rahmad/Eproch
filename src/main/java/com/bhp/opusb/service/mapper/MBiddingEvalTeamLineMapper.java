package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingEvalTeamLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingEvalTeamLine} and its DTO {@link MBiddingEvalTeamLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingEvaluationTeamMapper.class, AdUserMapper.class})
public interface MBiddingEvalTeamLineMapper extends EntityMapper<MBiddingEvalTeamLineDTO, MBiddingEvalTeamLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "evaluationTeam.id", target = "evaluationTeamId")
    @Mapping(source = "adUser.id", target = "adUserId")
    @Mapping(source = "adUser.position", target = "adUserPosition")
    @Mapping(source = "adUser.user.firstName", target = "adUserName")
    @Mapping(source = "adUser.user.lastName", target = "adUserLastName")
    @Mapping(source = "adUser.user.email", target = "adUserEmail")
    MBiddingEvalTeamLineDTO toDto(MBiddingEvalTeamLine mBiddingEvalTeamLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "evaluationTeamId", target = "evaluationTeam")
    @Mapping(source = "adUserId", target = "adUser")
    MBiddingEvalTeamLine toEntity(MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO);

    default MBiddingEvalTeamLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingEvalTeamLine mBiddingEvalTeamLine = new MBiddingEvalTeamLine();
        mBiddingEvalTeamLine.setId(id);
        return mBiddingEvalTeamLine;
    }
}
