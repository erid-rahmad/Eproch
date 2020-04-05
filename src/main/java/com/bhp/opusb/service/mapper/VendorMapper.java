package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.VendorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vendor} and its DTO {@link VendorDTO}.
 */
@Mapper(componentModel = "spring", uses = {BusinessCategoryMapper.class})
public interface VendorMapper extends EntityMapper<VendorDTO, Vendor> {


    @Mapping(target = "companyFunctionaries", ignore = true)
    @Mapping(target = "removeCompanyFunctionary", ignore = true)
    @Mapping(target = "locations", ignore = true)
    @Mapping(target = "removeLocation", ignore = true)
    @Mapping(target = "personInCharges", ignore = true)
    @Mapping(target = "removePersonInCharge", ignore = true)
    @Mapping(target = "supportingDocuments", ignore = true)
    @Mapping(target = "removeSupportingDocument", ignore = true)
    @Mapping(target = "removeBusinessCategory", ignore = true)
    Vendor toEntity(VendorDTO vendorDTO);

    default Vendor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vendor vendor = new Vendor();
        vendor.setId(id);
        return vendor;
    }
}
