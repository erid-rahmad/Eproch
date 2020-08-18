package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CVendorTax;
import com.bhp.opusb.service.dto.CVendorTaxDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CVendorTax} and its DTO {@link CVendorTaxDTO}.
 */
@Mapper(componentModel = "spring", uses = {CVendorMapper.class, CTaxMapper.class, ADOrganizationMapper.class})
public interface CVendorTaxMapper extends EntityMapper<CVendorTaxDTO, CVendorTax> {

    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "tax.id", target = "taxId")
    @Mapping(source = "tax.name", target = "taxName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CVendorTaxDTO toDto(CVendorTax cVendorTax);

    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "taxId", target = "tax")
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
