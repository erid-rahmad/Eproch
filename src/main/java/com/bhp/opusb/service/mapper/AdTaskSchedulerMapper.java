package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdTaskSchedulerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdTaskScheduler} and its DTO {@link AdTaskSchedulerDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdTaskMapper.class})
public interface AdTaskSchedulerMapper extends EntityMapper<AdTaskSchedulerDTO, AdTaskScheduler> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adTask.id", target = "adTaskId")
    @Mapping(source = "adTask.name", target = "adTaskName")
    AdTaskSchedulerDTO toDto(AdTaskScheduler adTaskScheduler);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adTaskId", target = "adTask")
    AdTaskScheduler toEntity(AdTaskSchedulerDTO adTaskSchedulerDTO);

    default AdTaskScheduler fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdTaskScheduler adTaskScheduler = new AdTaskScheduler();
        adTaskScheduler.setId(id);
        return adTaskScheduler;
    }
}
