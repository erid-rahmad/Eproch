package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CTaxRateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CTaxRate} and its DTO {@link CTaxRateDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CTaxCategoryMapper.class})
public interface CTaxRateMapper extends EntityMapper<CTaxRateDTO, CTaxRate> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "taxCategory.id", target = "taxCategoryId")
    @Mapping(source = "taxCategory.name", target = "taxCategoryName")
    CTaxRateDTO toDto(CTaxRate cTaxRate);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "taxCategoryId", target = "taxCategory")
    CTaxRate toEntity(CTaxRateDTO cTaxRateDTO);

    default CTaxRate fromId(Long id) {
        if (id == null) {
            return null;
        }
        CTaxRate cTaxRate = new CTaxRate();
        cTaxRate.setId(id);
        return cTaxRate;
    }
}
