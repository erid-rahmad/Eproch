package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CTaxInvoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CTaxInvoice} and its DTO {@link CTaxInvoiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CVendorMapper.class})
public interface CTaxInvoiceMapper extends EntityMapper<CTaxInvoiceDTO, CTaxInvoice> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.code", target = "vendorCode")
    @Mapping(source = "vendor.name", target = "vendorName")
    CTaxInvoiceDTO toDto(CTaxInvoice cTaxInvoice);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "vendorId", target = "vendor")
    CTaxInvoice toEntity(CTaxInvoiceDTO cTaxInvoiceDTO);

    default CTaxInvoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        CTaxInvoice cTaxInvoice = new CTaxInvoice();
        cTaxInvoice.setId(id);
        return cTaxInvoice;
    }
}
