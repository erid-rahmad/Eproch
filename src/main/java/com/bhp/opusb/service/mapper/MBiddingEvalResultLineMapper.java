package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingEvalResultLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingEvalResultLine} and its DTO {@link MBiddingEvalResultLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CEvaluationMethodLineMapper.class, MBiddingEvalResultMapper.class})
public interface MBiddingEvalResultLineMapper extends EntityMapper<MBiddingEvalResultLineDTO, MBiddingEvalResultLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "evaluationMethodLine.id", target = "evaluationMethodLineId")
    @Mapping(source = "evaluationMethodLine.evaluation", target = "evaluationMethodLineName")
    @Mapping(source = "biddingEvalResult.id", target = "biddingEvalResultId")
    MBiddingEvalResultLineDTO toDto(MBiddingEvalResultLine mBiddingEvalResultLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "evaluationMethodLineId", target = "evaluationMethodLine")
    @Mapping(source = "biddingEvalResultId", target = "biddingEvalResult")
    MBiddingEvalResultLine toEntity(MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO);

    default MBiddingEvalResultLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingEvalResultLine mBiddingEvalResultLine = new MBiddingEvalResultLine();
        mBiddingEvalResultLine.setId(id);
        return mBiddingEvalResultLine;
    }
}
