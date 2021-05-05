package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CEvalMethodSubCriteriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CEvalMethodSubCriteria} and its DTO {@link CEvalMethodSubCriteriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CBiddingCriteriaMapper.class, CBiddingSubCriteriaMapper.class, CEvaluationMethodCriteriaMapper.class})
public interface CEvalMethodSubCriteriaMapper extends EntityMapper<CEvalMethodSubCriteriaDTO, CEvalMethodSubCriteria> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingCriteria.id", target = "biddingCriteriaId")
    @Mapping(source = "biddingCriteria.name", target = "biddingCriteriaName")
    @Mapping(source = "biddingSubCriteria.id", target = "biddingSubCriteriaId")
    @Mapping(source = "biddingSubCriteria.name", target = "biddingSubCriteriaName")
    @Mapping(source = "evaluationMethodCriteria.id", target = "evaluationMethodCriteriaId")
    CEvalMethodSubCriteriaDTO toDto(CEvalMethodSubCriteria cEvalMethodSubCriteria);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingCriteriaId", target = "biddingCriteria")
    @Mapping(source = "biddingSubCriteriaId", target = "biddingSubCriteria")
    @Mapping(source = "evaluationMethodCriteriaId", target = "evaluationMethodCriteria")
    CEvalMethodSubCriteria toEntity(CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO);

    default CEvalMethodSubCriteria fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEvalMethodSubCriteria cEvalMethodSubCriteria = new CEvalMethodSubCriteria();
        cEvalMethodSubCriteria.setId(id);
        return cEvalMethodSubCriteria;
    }
}
