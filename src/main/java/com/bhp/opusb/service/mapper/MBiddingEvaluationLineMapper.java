package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingEvaluationLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingEvaluationLine} and its DTO {@link MBiddingEvaluationLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingEvaluationMapper.class})
public interface MBiddingEvaluationLineMapper extends EntityMapper<MBiddingEvaluationLineDTO, MBiddingEvaluationLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "biddingEvaluation.id", target = "biddingEvaluationId")
    MBiddingEvaluationLineDTO toDto(MBiddingEvaluationLine mBiddingEvaluationLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingEvaluationId", target = "biddingEvaluation")
    MBiddingEvaluationLine toEntity(MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO);

    default MBiddingEvaluationLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingEvaluationLine mBiddingEvaluationLine = new MBiddingEvaluationLine();
        mBiddingEvaluationLine.setId(id);
        return mBiddingEvaluationLine;
    }
}
