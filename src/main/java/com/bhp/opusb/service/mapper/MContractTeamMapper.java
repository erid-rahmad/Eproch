package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MContractTeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MContractTeam} and its DTO {@link MContractTeamDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MContractMapper.class})
public interface MContractTeamMapper extends EntityMapper<MContractTeamDTO, MContractTeam> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "contract.documentNo", target = "contractNo")
    @Mapping(source = "contract.name", target = "contractName")
    @Mapping(source = "contract.documentType.name", target = "contractTypeName")
    @Mapping(source = "contract.vendor.id", target = "vendorId")
    @Mapping(source = "contract.vendor.name", target = "vendorName")
    @Mapping(source = "contract.costCenter.name", target = "costCenterName")
    MContractTeamDTO toDto(MContractTeam mContractTeam);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "contractId", target = "contract")
    MContractTeam toEntity(MContractTeamDTO mContractTeamDTO);

    default MContractTeam fromId(Long id) {
        if (id == null) {
            return null;
        }
        MContractTeam mContractTeam = new MContractTeam();
        mContractTeam.setId(id);
        return mContractTeam;
    }
}
