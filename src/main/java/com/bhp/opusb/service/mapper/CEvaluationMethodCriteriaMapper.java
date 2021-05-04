package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CEvaluationMethodCriteriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CEvaluationMethodCriteria} and its DTO {@link CEvaluationMethodCriteriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CEvaluationMethodLineMapper.class, CBiddingCriteriaMapper.class})
public interface CEvaluationMethodCriteriaMapper extends EntityMapper<CEvaluationMethodCriteriaDTO, CEvaluationMethodCriteria> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "evaluationMethodLine.id", target = "evaluationMethodLineId")
    @Mapping(source = "evaluationMethodLine.evaluation", target = "evaluationMethodLineName")
    @Mapping(source = "biddingCriteria.id", target = "biddingCriteriaId")
    @Mapping(source = "biddingCriteria.name", target = "biddingCriteriaName")
    CEvaluationMethodCriteriaDTO toDto(CEvaluationMethodCriteria cEvaluationMethodCriteria);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "evaluationMethodLineId", target = "evaluationMethodLine")
    @Mapping(source = "biddingCriteriaId", target = "biddingCriteria")
    CEvaluationMethodCriteria toEntity(CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO);

    default CEvaluationMethodCriteria fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEvaluationMethodCriteria cEvaluationMethodCriteria = new CEvaluationMethodCriteria();
        cEvaluationMethodCriteria.setId(id);
        return cEvaluationMethodCriteria;
    }
}
