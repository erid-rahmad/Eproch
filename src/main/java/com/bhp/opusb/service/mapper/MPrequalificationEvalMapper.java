package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalificationEvalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalificationEval} and its DTO {@link MPrequalificationEvalDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MPrequalificationSubmissionMapper.class, CBiddingSubCriteriaLineMapper.class})
public interface MPrequalificationEvalMapper extends EntityMapper<MPrequalificationEvalDTO, MPrequalificationEval> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "prequalificationSubmission.id", target = "prequalificationSubmissionId")
    @Mapping(source = "biddingSubCriteriaLine.id", target = "biddingSubCriteriaLineId")
    MPrequalificationEvalDTO toDto(MPrequalificationEval mPrequalificationEval);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "prequalificationSubmissionId", target = "prequalificationSubmission")
    @Mapping(source = "biddingSubCriteriaLineId", target = "biddingSubCriteriaLine")
    MPrequalificationEval toEntity(MPrequalificationEvalDTO mPrequalificationEvalDTO);

    default MPrequalificationEval fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalificationEval mPrequalificationEval = new MPrequalificationEval();
        mPrequalificationEval.setId(id);
        return mPrequalificationEval;
    }
}
