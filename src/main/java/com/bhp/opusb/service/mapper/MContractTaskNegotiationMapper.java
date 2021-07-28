package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MContractTaskNegotiationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MContractTaskNegotiation} and its DTO {@link MContractTaskNegotiationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MContractTaskMapper.class, CAttachmentMapper.class})
public interface MContractTaskNegotiationMapper extends EntityMapper<MContractTaskNegotiationDTO, MContractTaskNegotiation> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "contractTask.id", target = "contractTaskId")
    @Mapping(source = "CAttachment.id", target = "CAttachmentId")
    MContractTaskNegotiationDTO toDto(MContractTaskNegotiation mContractTaskNegotiation);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "contractTaskId", target = "contractTask")
    @Mapping(source = "CAttachmentId", target = "CAttachment")
    MContractTaskNegotiation toEntity(MContractTaskNegotiationDTO mContractTaskNegotiationDTO);

    default MContractTaskNegotiation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MContractTaskNegotiation mContractTaskNegotiation = new MContractTaskNegotiation();
        mContractTaskNegotiation.setId(id);
        return mContractTaskNegotiation;
    }
}
