package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MVerification;
import com.bhp.opusb.service.dto.MVerificationDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MVerification} and its DTO {@link MVerificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CCurrencyMapper.class, CVendorMapper.class, CTaxCategoryMapper.class, CTaxMapper.class})
public interface MVerificationMapper extends EntityMapper<MVerificationDTO, MVerification> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.code", target = "adOrganizationCode")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.code", target = "currencyName")
    @Mapping(source = "matchPoCurrency.id", target = "matchPoCurrencyId")
    @Mapping(source = "matchPoCurrency.code", target = "matchPoCurrencyName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.code", target = "vendorCode")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "vendor.taxIdNo", target = "vendorTaxId")
    @Mapping(source = "vendorTo.id", target = "vendorToId")
    @Mapping(source = "vendorTo.name", target = "vendorToName")
    @Mapping(source = "CTaxCategory.id", target = "CTaxCategoryId")
    @Mapping(source = "CTaxCategory.name", target = "CTaxCategoryName")
    @Mapping(source = "CTax.id", target = "CTaxId")
    @Mapping(source = "CTax.name", target = "CTaxName")
    MVerificationDTO toDto(MVerification mVerification);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "matchPoCurrencyId", target = "matchPoCurrency")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "vendorToId", target = "vendorTo")
    @Mapping(source = "CTaxCategoryId", target = "CTaxCategory")
    @Mapping(source = "CTaxId", target = "CTax")
    MVerification toEntity(MVerificationDTO mVerificationDTO);

    default MVerification fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVerification mVerification = new MVerification();
        mVerification.setId(id);
        return mVerification;
    }
}
