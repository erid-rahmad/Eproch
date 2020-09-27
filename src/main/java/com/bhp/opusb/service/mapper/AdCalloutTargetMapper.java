package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdCalloutTargetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdCalloutTarget} and its DTO {@link AdCalloutTargetDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdCalloutMapper.class})
public interface AdCalloutTargetMapper extends EntityMapper<AdCalloutTargetDTO, AdCalloutTarget> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "callout.id", target = "calloutId")
    @Mapping(source = "callout.name", target = "calloutName")
    AdCalloutTargetDTO toDto(AdCalloutTarget adCalloutTarget);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "calloutId", target = "callout")
    AdCalloutTarget toEntity(AdCalloutTargetDTO adCalloutTargetDTO);

    default AdCalloutTarget fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdCalloutTarget adCalloutTarget = new AdCalloutTarget();
        adCalloutTarget.setId(id);
        return adCalloutTarget;
    }
}
