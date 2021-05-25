package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MTechProposalEvaluationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MTechProposalEvaluation} and its DTO {@link MTechProposalEvaluationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingMapper.class, CEvaluationMethodCriteriaMapper.class, CEvalMethodSubCriteriaMapper.class, CBiddingSubCriteriaLineMapper.class, CVendorMapper.class})
public interface MTechProposalEvaluationMapper extends EntityMapper<MTechProposalEvaluationDTO, MTechProposalEvaluation> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "evaluationMethodCriteria.id", target = "evaluationMethodCriteriaId")
    @Mapping(source = "evalMethodSubCriteria.id", target = "evalMethodSubCriteriaId")
    @Mapping(source = "biddingSubCriteriaLine.id", target = "biddingSubCriteriaLineId")
    @Mapping(source = "vendor.id", target = "vendorId")
    MTechProposalEvaluationDTO toDto(MTechProposalEvaluation mTechProposalEvaluation);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "evaluationMethodCriteriaId", target = "evaluationMethodCriteria")
    @Mapping(source = "evalMethodSubCriteriaId", target = "evalMethodSubCriteria")
    @Mapping(source = "biddingSubCriteriaLineId", target = "biddingSubCriteriaLine")
    @Mapping(source = "vendorId", target = "vendor")
    MTechProposalEvaluation toEntity(MTechProposalEvaluationDTO mTechProposalEvaluationDTO);

    default MTechProposalEvaluation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MTechProposalEvaluation mTechProposalEvaluation = new MTechProposalEvaluation();
        mTechProposalEvaluation.setId(id);
        return mTechProposalEvaluation;
    }
}
