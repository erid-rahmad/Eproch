package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdTaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdTask} and its DTO {@link AdTaskDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface AdTaskMapper extends EntityMapper<AdTaskDTO, AdTask> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    AdTaskDTO toDto(AdTask adTask);

    @Mapping(target = "adTaskProcesses", ignore = true)
    @Mapping(target = "removeAdTaskProcess", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    AdTask toEntity(AdTaskDTO adTaskDTO);

    default AdTask fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdTask adTask = new AdTask();
        adTask.setId(id);
        return adTask;
    }
}
