package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVendorSuggestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVendorSuggestion} and its DTO {@link MVendorSuggestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, ADOrganizationMapper.class, CBusinessCategoryMapper.class, CVendorMapper.class})
public interface MVendorSuggestionMapper extends EntityMapper<MVendorSuggestionDTO, MVendorSuggestion> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "businessSubCategory.id", target = "businessSubCategoryId")
    @Mapping(source = "businessSubCategory.name", target = "businessSubCategoryName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MVendorSuggestionDTO toDto(MVendorSuggestion mVendorSuggestion);

    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "businessSubCategoryId", target = "businessSubCategory")
    @Mapping(source = "vendorId", target = "vendor")
    MVendorSuggestion toEntity(MVendorSuggestionDTO mVendorSuggestionDTO);

    default MVendorSuggestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorSuggestion mVendorSuggestion = new MVendorSuggestion();
        mVendorSuggestion.setId(id);
        return mVendorSuggestion;
    }
}
