package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.ADFieldDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ADField} and its DTO {@link ADFieldDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADClientMapper.class, ADOrganizationMapper.class, ADReferenceMapper.class, ADColumnMapper.class, ADTabMapper.class})
public interface ADFieldMapper extends EntityMapper<ADFieldDTO, ADField> {

    @Mapping(source = "adClient.id", target = "adClientId")
    @Mapping(source = "adClient.name", target = "adClientName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adTab.id", target = "adTabId")
    @Mapping(source = "adColumn.id", target = "adColumnId")
    @Mapping(source = "adColumn.sqlName", target = "adColumnName")
    @Mapping(source = "adReference.id", target = "adReferenceId")
    @Mapping(source = "adReference.name", target = "adReferenceName")
    ADFieldDTO toDto(ADField aDField);

    @Mapping(source = "adClientId", target = "adClient")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adReferenceId", target = "adReference")
    @Mapping(source = "adColumnId", target = "adColumn")
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
