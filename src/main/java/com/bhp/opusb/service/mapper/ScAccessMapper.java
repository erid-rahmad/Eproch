package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.ScAccess;
import com.bhp.opusb.service.dto.ScAccessDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ScAccess} and its DTO {@link ScAccessDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, ScAccessTypeMapper.class, ADWindowMapper.class, AdFormMapper.class, ScAuthorityMapper.class})
public interface ScAccessMapper extends EntityMapper<ScAccessDTO, ScAccess> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    @Mapping(source = "window.id", target = "windowId")
    @Mapping(source = "window.name", target = "windowName")
    @Mapping(source = "form.id", target = "formId")
    @Mapping(source = "form.name", target = "formName")
    @Mapping(source = "authority.id", target = "authorityId")
    @Mapping(source = "authority.authority.name", target = "authorityName")
    ScAccessDTO toDto(ScAccess scAccess);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "windowId", target = "window")
    @Mapping(source = "formId", target = "form")
    @Mapping(source = "authorityId", target = "authority")
    ScAccess toEntity(ScAccessDTO scAccessDTO);

    default ScAccess fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScAccess scAccess = new ScAccess();
        scAccess.setId(id);
        return scAccess;
    }
}
