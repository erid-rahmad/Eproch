package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CTaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CTask} and its DTO {@link CTaskDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CTaskMapper extends EntityMapper<CTaskDTO, CTask> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CTaskDTO toDto(CTask cTask);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CTask toEntity(CTaskDTO cTaskDTO);

    default CTask fromId(Long id) {
        if (id == null) {
            return null;
        }
        CTask cTask = new CTask();
        cTask.setId(id);
        return cTask;
    }
}
