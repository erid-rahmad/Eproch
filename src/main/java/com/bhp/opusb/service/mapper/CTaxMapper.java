package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CTax;
import com.bhp.opusb.service.dto.CTaxDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CTax} and its DTO {@link CTaxDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CTaxCategoryMapper.class})
public interface CTaxMapper extends EntityMapper<CTaxDTO, CTax> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "taxCategory.id", target = "taxCategoryId")
    @Mapping(source = "taxCategory.name", target = "taxCategoryName")
    CTaxDTO toDto(CTax cTax);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "taxCategoryId", target = "taxCategory")
    CTax toEntity(CTaxDTO cTaxDTO);

    default CTax fromId(Long id) {
        if (id == null) {
            return null;
        }
        CTax cTax = new CTax();
        cTax.setId(id);
        return cTax;
    }
}
