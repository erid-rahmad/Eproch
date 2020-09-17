package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.ScAccessType;
import com.bhp.opusb.service.dto.ScAccessTypeDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ScAccessType} and its DTO {@link ScAccessTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface ScAccessTypeMapper extends EntityMapper<ScAccessTypeDTO, ScAccessType> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    ScAccessTypeDTO toDto(ScAccessType scAccessType);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    ScAccessType toEntity(ScAccessTypeDTO scAccessTypeDTO);

    default ScAccessType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScAccessType scAccessType = new ScAccessType();
        scAccessType.setId(id);
        return scAccessType;
    }
}
