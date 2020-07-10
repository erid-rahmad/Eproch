package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.ADField;
import com.bhp.opusb.service.dto.ADFieldDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ADField} and its DTO {@link ADFieldDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, ADReferenceMapper.class, ADColumnMapper.class, AdValidationRuleMapper.class, AdButtonMapper.class, ADTabMapper.class})
public interface ADFieldMapper extends EntityMapper<ADFieldDTO, ADField> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adReference.id", target = "adReferenceId")
    @Mapping(source = "adReference.name", target = "adReferenceName")
    @Mapping(source = "adColumn.id", target = "adColumnId")
    @Mapping(source = "adColumn.name", target = "adColumnName")
    @Mapping(source = "adValidationRule.id", target = "adValidationRuleId")
    @Mapping(source = "adValidationRule.name", target = "adValidationRuleName")
    @Mapping(source = "adButton.id", target = "adButtonId")
    @Mapping(source = "adButton.name", target = "adButtonName")
    @Mapping(source = "adTab.id", target = "adTabId")
    @Mapping(source = "adTab.name", target = "adTabName")
    ADFieldDTO toDto(ADField aDField);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adReferenceId", target = "adReference")
    @Mapping(source = "adColumnId", target = "adColumn")
    @Mapping(source = "adValidationRuleId", target = "adValidationRule")
    @Mapping(source = "adButtonId", target = "adButton")
    @Mapping(source = "adTabId", target = "adTab")
    ADField toEntity(ADFieldDTO aDFieldDTO);

    default ADField fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADField aDField = new ADField();
        aDField.setId(id);
        return aDField;
    }
}
