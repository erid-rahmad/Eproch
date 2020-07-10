package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdTaskProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdTaskProcess} and its DTO {@link AdTaskProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdTaskApplicationMapper.class, AdTaskMapper.class})
public interface AdTaskProcessMapper extends EntityMapper<AdTaskProcessDTO, AdTaskProcess> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adTaskApplication.id", target = "adTaskApplicationId")
    @Mapping(source = "adTaskApplication.name", target = "adTaskApplicationName")
    @Mapping(source = "adTask.id", target = "adTaskId")
    @Mapping(source = "adTask.name", target = "adTaskName")
    AdTaskProcessDTO toDto(AdTaskProcess adTaskProcess);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adTaskApplicationId", target = "adTaskApplication")
    @Mapping(source = "adTaskId", target = "adTask")
    AdTaskProcess toEntity(AdTaskProcessDTO adTaskProcessDTO);

    default AdTaskProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdTaskProcess adTaskProcess = new AdTaskProcess();
        adTaskProcess.setId(id);
        return adTaskProcess;
    }
}
