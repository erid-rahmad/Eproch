package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdCalloutDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdCallout} and its DTO {@link AdCalloutDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdTriggerMapper.class, ADFieldMapper.class})
public interface AdCalloutMapper extends EntityMapper<AdCalloutDTO, AdCallout> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "trigger.id", target = "triggerId")
    @Mapping(source = "trigger.name", target = "triggerName")
    @Mapping(source = "field.id", target = "fieldId")
    @Mapping(source = "field.name", target = "fieldName")
    AdCalloutDTO toDto(AdCallout adCallout);

    @Mapping(target = "adCalloutTargets", ignore = true)
    @Mapping(target = "removeAdCalloutTarget", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "triggerId", target = "trigger")
    @Mapping(source = "fieldId", target = "field")
    AdCallout toEntity(AdCalloutDTO adCalloutDTO);

    default AdCallout fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdCallout adCallout = new AdCallout();
        adCallout.setId(id);
        return adCallout;
    }
}
