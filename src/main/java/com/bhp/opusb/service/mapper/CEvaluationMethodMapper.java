package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CEvaluationMethodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CEvaluationMethod} and its DTO {@link CEvaluationMethodDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CBiddingTypeMapper.class, CEventTypeMapper.class})
public interface CEvaluationMethodMapper extends EntityMapper<CEvaluationMethodDTO, CEvaluationMethod> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingType.id", target = "biddingTypeId")
    @Mapping(source = "eventType.id", target = "eventTypeId")
    @Mapping(source = "eventType.name", target = "eventTypeName")
    @Mapping(source = "biddingType.name", target = "biddingTypeName")
    CEvaluationMethodDTO toDto(CEvaluationMethod cEvaluationMethod);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingTypeId", target = "biddingType")
    @Mapping(source = "eventTypeId", target = "eventType")
    CEvaluationMethod toEntity(CEvaluationMethodDTO cEvaluationMethodDTO);

    default CEvaluationMethod fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEvaluationMethod cEvaluationMethod = new CEvaluationMethod();
        cEvaluationMethod.setId(id);
        return cEvaluationMethod;
    }
}
