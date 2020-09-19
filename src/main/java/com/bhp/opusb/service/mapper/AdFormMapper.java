package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.AdForm;
import com.bhp.opusb.service.dto.AdFormDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AdForm} and its DTO {@link AdFormDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface AdFormMapper extends EntityMapper<AdFormDTO, AdForm> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    AdFormDTO toDto(AdForm adForm);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    AdForm toEntity(AdFormDTO adFormDTO);

    default AdForm fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdForm adForm = new AdForm();
        adForm.setId(id);
        return adForm;
    }
}
