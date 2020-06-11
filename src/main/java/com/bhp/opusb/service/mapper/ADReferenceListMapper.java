package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.ADReferenceListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ADReferenceList} and its DTO {@link ADReferenceListDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, ADReferenceMapper.class})
public interface ADReferenceListMapper extends EntityMapper<ADReferenceListDTO, ADReferenceList> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adReference.id", target = "adReferenceId")
    ADReferenceListDTO toDto(ADReferenceList aDReferenceList);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adReferenceId", target = "adReference")
    ADReferenceList toEntity(ADReferenceListDTO aDReferenceListDTO);

    default ADReferenceList fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADReferenceList aDReferenceList = new ADReferenceList();
        aDReferenceList.setId(id);
        return aDReferenceList;
    }
}
