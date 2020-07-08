package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdTriggerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdTrigger} and its DTO {@link AdTriggerDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface AdTriggerMapper extends EntityMapper<AdTriggerDTO, AdTrigger> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    AdTriggerDTO toDto(AdTrigger adTrigger);

    @Mapping(target = "adTriggerParams", ignore = true)
    @Mapping(target = "removeAdTriggerParam", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    AdTrigger toEntity(AdTriggerDTO adTriggerDTO);

    default AdTrigger fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdTrigger adTrigger = new AdTrigger();
        adTrigger.setId(id);
        return adTrigger;
    }
}
