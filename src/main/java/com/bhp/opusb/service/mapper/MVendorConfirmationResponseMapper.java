package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVendorConfirmationResponseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVendorConfirmationResponse} and its DTO {@link MVendorConfirmationResponseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MVendorConfirmationLineMapper.class, MVendorConfirmationContractMapper.class})
public interface MVendorConfirmationResponseMapper extends EntityMapper<MVendorConfirmationResponseDTO, MVendorConfirmationResponse> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "vendorConfirmationLine.id", target = "vendorConfirmationLineId")
    @Mapping(source = "vendorConfirmationContract.id", target = "vendorConfirmationContractId")
    @Mapping(source = "vendorConfirmationContract.confirmationNo", target = "confirmationNo")
    MVendorConfirmationResponseDTO toDto(MVendorConfirmationResponse mVendorConfirmationResponse);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "vendorConfirmationLineId", target = "vendorConfirmationLine")
    @Mapping(source = "vendorConfirmationContractId", target = "vendorConfirmationContract")
    MVendorConfirmationResponse toEntity(MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO);

    default MVendorConfirmationResponse fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorConfirmationResponse mVendorConfirmationResponse = new MVendorConfirmationResponse();
        mVendorConfirmationResponse.setId(id);
        return mVendorConfirmationResponse;
    }
}
