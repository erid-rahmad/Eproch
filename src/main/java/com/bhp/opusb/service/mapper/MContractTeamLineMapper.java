package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MContractTeamLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MContractTeamLine} and its DTO {@link MContractTeamLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MContractTeamMapper.class, AdUserMapper.class})
public interface MContractTeamLineMapper extends EntityMapper<MContractTeamLineDTO, MContractTeamLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "contractTeam.id", target = "contractTeamId")
    @Mapping(source = "adUser.id", target = "adUserId")
    @Mapping(source = "adUser.position", target = "adUserPosition")
    @Mapping(source = "adUser.user.firstName", target = "adUserName")
    @Mapping(source = "adUser.user.lastName", target = "adUserLastName")
    @Mapping(source = "adUser.user.email", target = "adUserEmail")
    MContractTeamLineDTO toDto(MContractTeamLine mContractTeamLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "contractTeamId", target = "contractTeam")
    @Mapping(source = "adUserId", target = "adUser")
    MContractTeamLine toEntity(MContractTeamLineDTO mContractTeamLineDTO);

    default MContractTeamLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MContractTeamLine mContractTeamLine = new MContractTeamLine();
        mContractTeamLine.setId(id);
        return mContractTeamLine;
    }
}
