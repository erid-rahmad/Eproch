package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVerificationLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVerificationLine} and its DTO {@link MVerificationLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MVerificationMapper.class, ADOrganizationMapper.class, CProductMapper.class, CUnitOfMeasureMapper.class, CCostCenterMapper.class, CCurrencyMapper.class})
public interface MVerificationLineMapper extends EntityMapper<MVerificationLineDTO, MVerificationLine> {

    @Mapping(source = "verification.id", target = "verificationId")
    @Mapping(source = "verification.verificationNo", target = "verificationNo")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.code", target = "adOrganizationCode")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.code", target = "productCode")
    @Mapping(source = "product.name", target = "productShortName")
    @Mapping(source = "product.description", target = "productDescription")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.code", target = "uomCode")
    @Mapping(source = "uom.name", target = "uomName")
    @Mapping(source = "CCostCenter.id", target = "CCostCenterId")
    @Mapping(source = "CCostCenter.code", target = "CCostCenterCode")
    @Mapping(source = "CCostCenter.name", target = "CCostCenterShortName")
    @Mapping(source = "CCurrency.id", target = "CCurrencyId")
    @Mapping(source = "CCurrency.code", target = "CCurrencyName")
    MVerificationLineDTO toDto(MVerificationLine mVerificationLine);

    @Mapping(source = "verificationId", target = "verification")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "uomId", target = "uom")
    @Mapping(source = "CCostCenterId", target = "CCostCenter")
    @Mapping(source = "CCurrencyId", target = "CCurrency")
    MVerificationLine toEntity(MVerificationLineDTO mVerificationLineDTO);

    default MVerificationLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVerificationLine mVerificationLine = new MVerificationLine();
        mVerificationLine.setId(id);
        return mVerificationLine;
    }
}
