package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MContractMessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MContractMessage} and its DTO {@link MContractMessageDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MContractMapper.class, CVendorMapper.class, CAttachmentMapper.class})
public interface MContractMessageMapper extends EntityMapper<MContractMessageDTO, MContractMessage> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.downloadUrl", target = "downloadUrl")
    MContractMessageDTO toDto(MContractMessage mContractMessage);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "contractId", target = "contract")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "attachmentId", target = "attachment")
    MContractMessage toEntity(MContractMessageDTO mContractMessageDTO);

    default MContractMessage fromId(Long id) {
        if (id == null) {
            return null;
        }
        MContractMessage mContractMessage = new MContractMessage();
        mContractMessage.setId(id);
        return mContractMessage;
    }
}
