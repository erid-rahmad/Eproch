package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CVendorBusinessCatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CVendorBusinessCat} and its DTO {@link CVendorBusinessCatDTO}.
 */
@Mapper(componentModel = "spring", uses = {CVendorMapper.class, CBusinessCategoryMapper.class, ADOrganizationMapper.class})
public interface CVendorBusinessCatMapper extends EntityMapper<CVendorBusinessCatDTO, CVendorBusinessCat> {

    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "businessClassification.id", target = "businessClassificationId")
    @Mapping(source = "businessClassification.name", target = "businessClassificationName")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "businessCategory.name", target = "businessCategoryName")
    @Mapping(source = "subBusinessCategory.id", target = "subBusinessCategoryId")
    @Mapping(source = "subBusinessCategory.name", target = "subBusinessCategoryName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CVendorBusinessCatDTO toDto(CVendorBusinessCat cVendorBusinessCat);

    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "businessClassificationId", target = "businessClassification")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    @Mapping(source = "subBusinessCategoryId", target = "subBusinessCategory")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CVendorBusinessCat toEntity(CVendorBusinessCatDTO cVendorBusinessCatDTO);

    default CVendorBusinessCat fromId(Long id) {
        if (id == null) {
            return null;
        }
        CVendorBusinessCat cVendorBusinessCat = new CVendorBusinessCat();
        cVendorBusinessCat.setId(id);
        return cVendorBusinessCat;
    }
}
