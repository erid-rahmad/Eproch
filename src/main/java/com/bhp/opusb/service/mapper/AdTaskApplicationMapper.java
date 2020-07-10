package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdTaskApplicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdTaskApplication} and its DTO {@link AdTaskApplicationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface AdTaskApplicationMapper extends EntityMapper<AdTaskApplicationDTO, AdTaskApplication> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    AdTaskApplicationDTO toDto(AdTaskApplication adTaskApplication);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    AdTaskApplication toEntity(AdTaskApplicationDTO adTaskApplicationDTO);

    default AdTaskApplication fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdTaskApplication adTaskApplication = new AdTaskApplication();
        adTaskApplication.setId(id);
        return adTaskApplication;
    }
}
