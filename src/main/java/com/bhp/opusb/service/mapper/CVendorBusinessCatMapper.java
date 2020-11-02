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
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "businessCategory.name", target = "businessCategoryName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CVendorBusinessCatDTO toDto(CVendorBusinessCat cVendorBusinessCat);

    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
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
