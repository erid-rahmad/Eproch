package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.ADTableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ADTable} and its DTO {@link ADTableDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADClientMapper.class, ADOrganizationMapper.class})
public interface ADTableMapper extends EntityMapper<ADTableDTO, ADTable> {

    @Mapping(source = "adClient.id", target = "adClientId")
    @Mapping(source = "adClient.name", target = "adClientName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    ADTableDTO toDto(ADTable aDTable);

    @Mapping(target = "aDColumns", ignore = true)
    @Mapping(target = "removeADColumn", ignore = true)
    @Mapping(source = "adClientId", target = "adClient")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    ADTable toEntity(ADTableDTO aDTableDTO);

    default ADTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADTable aDTable = new ADTable();
        aDTable.setId(id);
        return aDTable;
    }
}
