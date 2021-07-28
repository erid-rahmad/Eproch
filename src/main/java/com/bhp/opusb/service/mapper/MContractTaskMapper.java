package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MContractTaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MContractTask} and its DTO {@link MContractTaskDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MContractMapper.class, CTaskMapper.class})
public interface MContractTaskMapper extends EntityMapper<MContractTaskDTO, MContractTask> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "task.id", target = "taskId")
    MContractTaskDTO toDto(MContractTask mContractTask);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "contractId", target = "contract")
    @Mapping(source = "taskId", target = "task")
    MContractTask toEntity(MContractTaskDTO mContractTaskDTO);

    default MContractTask fromId(Long id) {
        if (id == null) {
            return null;
        }
        MContractTask mContractTask = new MContractTask();
        mContractTask.setId(id);
        return mContractTask;
    }
}
