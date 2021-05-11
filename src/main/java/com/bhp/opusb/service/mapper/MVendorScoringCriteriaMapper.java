package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVendorScoringCriteriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVendorScoringCriteria} and its DTO {@link MVendorScoringCriteriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CEvaluationMethodCriteriaMapper.class, CEvalMethodSubCriteriaMapper.class, MVendorScoringLineMapper.class, CBiddingSubCriteriaLineMapper.class})
public interface MVendorScoringCriteriaMapper extends EntityMapper<MVendorScoringCriteriaDTO, MVendorScoringCriteria> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "evaluationMethodCriteria.id", target = "evaluationMethodCriteriaId")
    @Mapping(source = "evalMethodSubCriteria.id", target = "evalMethodSubCriteriaId")
    @Mapping(source = "vendorScoringLine.id", target = "vendorScoringLineId")
    @Mapping(source = "biddingSubCriteriaLine.id", target = "biddingSubCriteriaLineId")
    MVendorScoringCriteriaDTO toDto(MVendorScoringCriteria mVendorScoringCriteria);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "evaluationMethodCriteriaId", target = "evaluationMethodCriteria")
    @Mapping(source = "evalMethodSubCriteriaId", target = "evalMethodSubCriteria")
    @Mapping(source = "vendorScoringLineId", target = "vendorScoringLine")
    @Mapping(source = "biddingSubCriteriaLineId", target = "biddingSubCriteriaLine")
    MVendorScoringCriteria toEntity(MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO);

    default MVendorScoringCriteria fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorScoringCriteria mVendorScoringCriteria = new MVendorScoringCriteria();
        mVendorScoringCriteria.setId(id);
        return mVendorScoringCriteria;
    }
}
