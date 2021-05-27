package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVendorConfirmationLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVendorConfirmationLine} and its DTO {@link MVendorConfirmationLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CVendorMapper.class, MBiddingEvalResultMapper.class, MVendorConfirmationMapper.class})
public interface MVendorConfirmationLineMapper extends EntityMapper<MVendorConfirmationLineDTO, MVendorConfirmationLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "biddingEvalResult.id", target = "biddingEvalResultId")
    @Mapping(source = "vendorConfirmation.id", target = "vendorConfirmationId")
    MVendorConfirmationLineDTO toDto(MVendorConfirmationLine mVendorConfirmationLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "biddingEvalResultId", target = "biddingEvalResult")
    @Mapping(source = "vendorConfirmationId", target = "vendorConfirmation")
    MVendorConfirmationLine toEntity(MVendorConfirmationLineDTO mVendorConfirmationLineDTO);

    default MVendorConfirmationLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorConfirmationLine mVendorConfirmationLine = new MVendorConfirmationLine();
        mVendorConfirmationLine.setId(id);
        return mVendorConfirmationLine;
    }
}
