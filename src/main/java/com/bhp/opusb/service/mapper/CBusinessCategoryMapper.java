package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CBusinessCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CBusinessCategory} and its DTO {@link CBusinessCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CBusinessCategoryMapper extends EntityMapper<CBusinessCategoryDTO, CBusinessCategory> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "parentCategory.id", target = "parentCategoryId")
    @Mapping(source = "parentCategory.name", target = "parentCategoryName")
    CBusinessCategoryDTO toDto(CBusinessCategory cBusinessCategory);

    @Mapping(target = "cBusinessCategories", ignore = true)
    @Mapping(target = "removeCBusinessCategory", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "parentCategoryId", target = "parentCategory")
    CBusinessCategory toEntity(CBusinessCategoryDTO cBusinessCategoryDTO);

    default CBusinessCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        CBusinessCategory cBusinessCategory = new CBusinessCategory();
        cBusinessCategory.setId(id);
        return cBusinessCategory;
    }
}
