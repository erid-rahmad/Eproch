package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVendorConfirmationContractDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVendorConfirmationContract} and its DTO {@link MVendorConfirmationContractDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CAttachmentMapper.class, MVendorConfirmationLineMapper.class})
public interface MVendorConfirmationContractMapper extends EntityMapper<MVendorConfirmationContractDTO, MVendorConfirmationContract> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.downloadUrl", target = "downloadUrl")
    @Mapping(source = "vendorConfirmationLine.id", target = "vendorConfirmationLineId")
    MVendorConfirmationContractDTO toDto(MVendorConfirmationContract mVendorConfirmationContract);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "attachmentId", target = "attachment")
    @Mapping(source = "vendorConfirmationLineId", target = "vendorConfirmationLine")
    MVendorConfirmationContract toEntity(MVendorConfirmationContractDTO mVendorConfirmationContractDTO);

    default MVendorConfirmationContract fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorConfirmationContract mVendorConfirmationContract = new MVendorConfirmationContract();
        mVendorConfirmationContract.setId(id);
        return mVendorConfirmationContract;
    }
}
