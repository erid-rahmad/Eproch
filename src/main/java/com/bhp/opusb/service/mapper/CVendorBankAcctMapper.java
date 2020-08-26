package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CVendorBankAcctDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CVendorBankAcct} and its DTO {@link CVendorBankAcctDTO}.
 */
@Mapper(componentModel = "spring", uses = {CVendorMapper.class, CBankMapper.class, CCurrencyMapper.class, CAttachmentMapper.class, ADOrganizationMapper.class})
public interface CVendorBankAcctMapper extends EntityMapper<CVendorBankAcctDTO, CVendorBankAcct> {

    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "bank.id", target = "bankId")
    @Mapping(source = "bank.name", target = "bankName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.name", target = "currencyName")
    @Mapping(source = "file.id", target = "fileId")
    @Mapping(source = "file.fileName", target = "fileName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CVendorBankAcctDTO toDto(CVendorBankAcct cVendorBankAcct);

    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "bankId", target = "bank")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "fileId", target = "file")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CVendorBankAcct toEntity(CVendorBankAcctDTO cVendorBankAcctDTO);

    default CVendorBankAcct fromId(Long id) {
        if (id == null) {
            return null;
        }
        CVendorBankAcct cVendorBankAcct = new CVendorBankAcct();
        cVendorBankAcct.setId(id);
        return cVendorBankAcct;
    }
}
