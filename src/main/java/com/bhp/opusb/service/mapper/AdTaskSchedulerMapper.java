package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.AdTaskScheduler;
import com.bhp.opusb.service.dto.AdTaskSchedulerDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AdTaskScheduler} and its DTO {@link AdTaskSchedulerDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdTaskMapper.class, AdTriggerMapper.class, AdTaskSchedulerGroupMapper.class})
public interface AdTaskSchedulerMapper extends EntityMapper<AdTaskSchedulerDTO, AdTaskScheduler> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adTask.id", target = "adTaskId")
    @Mapping(source = "adTask.name", target = "adTaskName")
    @Mapping(source = "adTrigger.id", target = "adTriggerId")
    @Mapping(source = "adTrigger.name", target = "adTriggerName")
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    AdTaskSchedulerDTO toDto(AdTaskScheduler adTaskScheduler);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adTaskId", target = "adTask")
    @Mapping(source = "adTriggerId", target = "adTrigger")
    @Mapping(source = "groupId", target = "group")
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
