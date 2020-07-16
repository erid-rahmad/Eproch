package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdTaskSchedulerGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdTaskSchedulerGroup} and its DTO {@link AdTaskSchedulerGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface AdTaskSchedulerGroupMapper extends EntityMapper<AdTaskSchedulerGroupDTO, AdTaskSchedulerGroup> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    AdTaskSchedulerGroupDTO toDto(AdTaskSchedulerGroup adTaskSchedulerGroup);

    @Mapping(target = "adTaskSchedulers", ignore = true)
    @Mapping(target = "removeAdTaskScheduler", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    AdTaskSchedulerGroup toEntity(AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO);

    default AdTaskSchedulerGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdTaskSchedulerGroup adTaskSchedulerGroup = new AdTaskSchedulerGroup();
        adTaskSchedulerGroup.setId(id);
        return adTaskSchedulerGroup;
    }
}
