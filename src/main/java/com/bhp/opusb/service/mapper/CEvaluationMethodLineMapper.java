package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CEvaluationMethodLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CEvaluationMethodLine} and its DTO {@link CEvaluationMethodLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CEvaluationMethodMapper.class})
public interface CEvaluationMethodLineMapper extends EntityMapper<CEvaluationMethodLineDTO, CEvaluationMethodLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "evaluationMethod.id", target = "evaluationMethodId")
    CEvaluationMethodLineDTO toDto(CEvaluationMethodLine cEvaluationMethodLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "evaluationMethodId", target = "evaluationMethod")
    CEvaluationMethodLine toEntity(CEvaluationMethodLineDTO cEvaluationMethodLineDTO);

    default CEvaluationMethodLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEvaluationMethodLine cEvaluationMethodLine = new CEvaluationMethodLine();
        cEvaluationMethodLine.setId(id);
        return cEvaluationMethodLine;
    }
}
