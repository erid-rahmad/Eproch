package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVendorInvitationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVendorInvitation} and its DTO {@link MVendorInvitationDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, ADOrganizationMapper.class, CBusinessCategoryMapper.class})
public interface MVendorInvitationMapper extends EntityMapper<MVendorInvitationDTO, MVendorInvitation> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "businessClassification.id", target = "businessClassificationId")
    @Mapping(source = "businessClassification.name", target = "businessClassificationName")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "businessCategory.name", target = "businessCategoryName")
    @Mapping(source = "businessSubCategory.id", target = "businessSubCategoryId")
    @Mapping(source = "businessSubCategory.name", target = "businessSubCategoryName")
    MVendorInvitationDTO toDto(MVendorInvitation mVendorInvitation);

    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "businessClassificationId", target = "businessClassification")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    @Mapping(source = "businessSubCategoryId", target = "businessSubCategory")
    MVendorInvitation toEntity(MVendorInvitationDTO mVendorInvitationDTO);

    default MVendorInvitation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorInvitation mVendorInvitation = new MVendorInvitation();
        mVendorInvitation.setId(id);
        return mVendorInvitation;
    }
}
