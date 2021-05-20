package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingEvaluationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingEvaluation} and its DTO {@link MBiddingEvaluationDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingSubmissionMapper.class, MBiddingScheduleMapper.class, ADOrganizationMapper.class})
public interface MBiddingEvaluationMapper extends EntityMapper<MBiddingEvaluationDTO, MBiddingEvaluation> {

    @Mapping(source = "biddingSubmission.id", target = "biddingSubmissionId")
    @Mapping(source = "biddingSchedule.id", target = "biddingScheduleId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    MBiddingEvaluationDTO toDto(MBiddingEvaluation mBiddingEvaluation);

    @Mapping(source = "biddingSubmissionId", target = "biddingSubmission")
    @Mapping(source = "biddingScheduleId", target = "biddingSchedule")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    MBiddingEvaluation toEntity(MBiddingEvaluationDTO mBiddingEvaluationDTO);

    default MBiddingEvaluation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingEvaluation mBiddingEvaluation = new MBiddingEvaluation();
        mBiddingEvaluation.setId(id);
        return mBiddingEvaluation;
    }
}
