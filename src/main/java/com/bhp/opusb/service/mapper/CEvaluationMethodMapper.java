package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CEvaluationMethod;
import com.bhp.opusb.service.dto.CEvaluationMethodDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CEvaluationMethod} and its DTO {@link CEvaluationMethodDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CBiddingTypeMapper.class})
public interface CEvaluationMethodMapper extends EntityMapper<CEvaluationMethodDTO, CEvaluationMethod> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingType.id", target = "biddingTypeId")
    @Mapping(source = "biddingType.name", target = "biddingTypeName")
    CEvaluationMethodDTO toDto(CEvaluationMethod cEvaluationMethod);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingTypeId", target = "biddingType")
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
