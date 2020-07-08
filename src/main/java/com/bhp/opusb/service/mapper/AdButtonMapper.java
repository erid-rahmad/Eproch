package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdButtonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdButton} and its DTO {@link AdButtonDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdTriggerMapper.class})
public interface AdButtonMapper extends EntityMapper<AdButtonDTO, AdButton> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adTrigger.id", target = "adTriggerId")
    @Mapping(source = "adTrigger.name", target = "adTriggerName")
    AdButtonDTO toDto(AdButton adButton);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adTriggerId", target = "adTrigger")
    AdButton toEntity(AdButtonDTO adButtonDTO);

    default AdButton fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdButton adButton = new AdButton();
        adButton.setId(id);
        return adButton;
    }
}
