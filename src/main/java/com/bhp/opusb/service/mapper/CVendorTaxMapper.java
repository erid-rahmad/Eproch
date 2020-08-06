package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CVendorTaxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CVendorTax} and its DTO {@link CVendorTaxDTO}.
 */
@Mapper(componentModel = "spring", uses = {CVendorMapper.class, CTaxCategoryMapper.class, CTaxRateMapper.class, ADOrganizationMapper.class})
public interface CVendorTaxMapper extends EntityMapper<CVendorTaxDTO, CVendorTax> {

    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "taxCategory.id", target = "taxCategoryId")
    @Mapping(source = "taxRate.id", target = "taxRateId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CVendorTaxDTO toDto(CVendorTax cVendorTax);

    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "taxCategoryId", target = "taxCategory")
    @Mapping(source = "taxRateId", target = "taxRate")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CVendorTax toEntity(CVendorTaxDTO cVendorTaxDTO);

    default CVendorTax fromId(Long id) {
        if (id == null) {
            return null;
        }
        CVendorTax cVendorTax = new CVendorTax();
        cVendorTax.setId(id);
        return cVendorTax;
    }
}
