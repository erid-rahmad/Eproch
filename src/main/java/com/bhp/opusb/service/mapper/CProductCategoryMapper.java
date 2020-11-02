package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CProductCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CProductCategory} and its DTO {@link CProductCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CProductCategoryMapper extends EntityMapper<CProductCategoryDTO, CProductCategory> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "parentCategory.id", target = "parentCategoryId")
    @Mapping(source = "parentCategory.name", target = "parentCategoryName")
    CProductCategoryDTO toDto(CProductCategory cProductCategory);

    @Mapping(target = "cProductCategories", ignore = true)
    @Mapping(target = "removeCProductCategory", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "parentCategoryId", target = "parentCategory")
    CProductCategory toEntity(CProductCategoryDTO cProductCategoryDTO);

    default CProductCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        CProductCategory cProductCategory = new CProductCategory();
        cProductCategory.setId(id);
        return cProductCategory;
    }
}
