package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MContractTaskReviewersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MContractTaskReviewers} and its DTO {@link MContractTaskReviewersDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MContractTaskMapper.class, AdUserMapper.class})
public interface MContractTaskReviewersMapper extends EntityMapper<MContractTaskReviewersDTO, MContractTaskReviewers> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "contractTask.id", target = "contractTaskId")
    @Mapping(source = "pic.id", target = "picId")
    MContractTaskReviewersDTO toDto(MContractTaskReviewers mContractTaskReviewers);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "contractTaskId", target = "contractTask")
    @Mapping(source = "picId", target = "pic")
    MContractTaskReviewers toEntity(MContractTaskReviewersDTO mContractTaskReviewersDTO);

    default MContractTaskReviewers fromId(Long id) {
        if (id == null) {
            return null;
        }
        MContractTaskReviewers mContractTaskReviewers = new MContractTaskReviewers();
        mContractTaskReviewers.setId(id);
        return mContractTaskReviewers;
    }
}
