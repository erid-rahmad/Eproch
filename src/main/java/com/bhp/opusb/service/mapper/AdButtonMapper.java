package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.AdButton;
import com.bhp.opusb.service.dto.AdButtonDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AdButton} and its DTO {@link AdButtonDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, ADTabMapper.class, AdTriggerMapper.class})
public interface AdButtonMapper extends EntityMapper<AdButtonDTO, AdButton> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adTab.id", target = "adTabId")
    @Mapping(source = "adTab.name", target = "adTabName")
    @Mapping(source = "adTrigger.id", target = "adTriggerId")
    @Mapping(source = "adTrigger.name", target = "adTriggerName")
    AdButtonDTO toDto(AdButton adButton);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adTabId", target = "adTab")
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
