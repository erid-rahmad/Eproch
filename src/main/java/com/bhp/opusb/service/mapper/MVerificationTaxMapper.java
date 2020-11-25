package com.bhp.opusb.service.mapper;

import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVerificationTaxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVerificationTax} and its DTO {@link MVerificationTaxDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CCurrencyMapper.class, CTaxCategoryMapper.class, CCostCenterMapper.class})
public interface MVerificationTaxMapper extends EntityMapper<MVerificationTaxDTO, MVerificationTax> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.name", target = "currencyName")
    @Mapping(source = "taxCategory.id", target = "taxCategoryId")
    @Mapping(source = "taxCategory.name", target = "taxCategoryName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    MVerificationTaxDTO toDto(MVerificationTax mVerificationTax);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "taxCategoryId", target = "taxCategory")
    @Mapping(source = "costCenterId", target = "costCenter")
    MVerificationTax toEntity(MVerificationTaxDTO mVerificationTaxDTO);

    default MVerificationTax fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVerificationTax mVerificationTax = new MVerificationTax();
        mVerificationTax.setId(id);
        return mVerificationTax;
    }
}
