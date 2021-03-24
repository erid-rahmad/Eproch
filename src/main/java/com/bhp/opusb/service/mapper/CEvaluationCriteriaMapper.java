package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CEvaluationCriteria;
import com.bhp.opusb.service.dto.CEvaluationCriteriaDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CEvaluationCriteria} and its DTO {@link CEvaluationCriteriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CEvaluationMethodLineMapper.class, CBiddingCriteriaMapper.class, CBiddingSubCriteriaMapper.class, AdUserMapper.class})
public interface CEvaluationCriteriaMapper extends EntityMapper<CEvaluationCriteriaDTO, CEvaluationCriteria> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "evaluationMethodLine.id", target = "evaluationMethodLineId")
    @Mapping(source = "evaluationMethodLine.evaluation", target = "evaluationMethodLineName")
    @Mapping(source = "biddingCriteria.id", target = "biddingCriteriaId")
    @Mapping(source = "biddingSubCriteria.name", target = "biddingSubCriteriaId")
    @Mapping(source = "pic.id", target = "picId")
    @Mapping(source = "pic.user.login", target = "picLogin")
    @Mapping(source = "pic.user.firstName", target = "picFirstName")
    @Mapping(source = "pic.user.lastName", target = "picLastName")
    CEvaluationCriteriaDTO toDto(CEvaluationCriteria cEvaluationCriteria);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "evaluationMethodLineId", target = "evaluationMethodLine")
    @Mapping(source = "biddingCriteriaId", target = "biddingCriteria")
    @Mapping(source = "biddingSubCriteriaId", target = "biddingSubCriteria")
    @Mapping(source = "picId", target = "pic")
    CEvaluationCriteria toEntity(CEvaluationCriteriaDTO cEvaluationCriteriaDTO);

    default CEvaluationCriteria fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEvaluationCriteria cEvaluationCriteria = new CEvaluationCriteria();
        cEvaluationCriteria.setId(id);
        return cEvaluationCriteria;
    }
}
