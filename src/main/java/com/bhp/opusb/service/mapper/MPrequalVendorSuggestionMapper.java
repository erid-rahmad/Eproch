package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalVendorSuggestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalVendorSuggestion} and its DTO {@link MPrequalVendorSuggestionDTO}.
 */
@Mapper(componentModel = "spring", uses = {MPrequalificationInformationMapper.class, ADOrganizationMapper.class, CBusinessCategoryMapper.class, CVendorMapper.class})
public interface MPrequalVendorSuggestionMapper extends EntityMapper<MPrequalVendorSuggestionDTO, MPrequalVendorSuggestion> {

    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "prequalification.name", target = "prequalificationName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "businessSubCategory.id", target = "businessSubCategoryId")
    @Mapping(source = "businessSubCategory.name", target = "businessSubCategoryName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.code", target = "vendorCode")
    @Mapping(source = "vendor.name", target = "vendorName")
    MPrequalVendorSuggestionDTO toDto(MPrequalVendorSuggestion mPrequalVendorSuggestion);

    @Mapping(source = "prequalificationId", target = "prequalification")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "businessSubCategoryId", target = "businessSubCategory")
    @Mapping(source = "vendorId", target = "vendor")
    MPrequalVendorSuggestion toEntity(MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO);

    default MPrequalVendorSuggestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalVendorSuggestion mPrequalVendorSuggestion = new MPrequalVendorSuggestion();
        mPrequalVendorSuggestion.setId(id);
        return mPrequalVendorSuggestion;
    }
}
