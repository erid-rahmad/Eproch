package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.service.dto.ADColumnDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ADColumn} and its DTO {@link ADColumnDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, ADReferenceMapper.class, AdValidationRuleMapper.class, ADTableMapper.class})
public interface ADColumnMapper extends EntityMapper<ADColumnDTO, ADColumn> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adReference.id", target = "adReferenceId")
    @Mapping(source = "adValidationRule.id", target = "adValidationRuleId")
    @Mapping(source = "adTable.id", target = "adTableId")
    ADColumnDTO toDto(ADColumn aDColumn);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adReferenceId", target = "adReference")
    @Mapping(source = "adValidationRuleId", target = "adValidationRule")
    @Mapping(source = "adTableId", target = "adTable")
    ADColumn toEntity(ADColumnDTO aDColumnDTO);

    default ADColumn fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADColumn aDColumn = new ADColumn();
        aDColumn.setId(id);
        return aDColumn;
    }
}
