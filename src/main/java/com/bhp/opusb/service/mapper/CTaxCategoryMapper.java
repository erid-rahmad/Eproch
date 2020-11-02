package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CTaxCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CTaxCategory} and its DTO {@link CTaxCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CTaxCategoryMapper extends EntityMapper<CTaxCategoryDTO, CTaxCategory> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CTaxCategoryDTO toDto(CTaxCategory cTaxCategory);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CTaxCategory toEntity(CTaxCategoryDTO cTaxCategoryDTO);

    default CTaxCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        CTaxCategory cTaxCategory = new CTaxCategory();
        cTaxCategory.setId(id);
        return cTaxCategory;
    }
}
