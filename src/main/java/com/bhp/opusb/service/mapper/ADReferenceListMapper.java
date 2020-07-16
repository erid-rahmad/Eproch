package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.ADReferenceList;
import com.bhp.opusb.service.dto.ADReferenceListDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ADReferenceList} and its DTO {@link ADReferenceListDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, ADReferenceMapper.class})
public interface ADReferenceListMapper extends EntityMapper<ADReferenceListDTO, ADReferenceList> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adReference.id", target = "adReferenceId")
    @Mapping(source = "adReference.name", target = "adReferenceName")
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
