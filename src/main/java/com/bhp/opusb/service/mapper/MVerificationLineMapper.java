package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVerificationLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVerificationLine} and its DTO {@link MVerificationLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MVerificationMapper.class, ADOrganizationMapper.class, CProductMapper.class, CUnitOfMeasureMapper.class})
public interface MVerificationLineMapper extends EntityMapper<MVerificationLineDTO, MVerificationLine> {

    @Mapping(source = "verification.id", target = "verificationId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.name", target = "uomName")
    MVerificationLineDTO toDto(MVerificationLine mVerificationLine);

    @Mapping(source = "verificationId", target = "verification")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "uomId", target = "uom")
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