package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.BusinessCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessCategory} and its DTO {@link BusinessCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BusinessCategoryMapper extends EntityMapper<BusinessCategoryDTO, BusinessCategory> {

    @Mapping(source = "parentCategory.id", target = "parentCategoryId")
    @Mapping(source = "parentCategory.name", target = "parentCategoryName")
    BusinessCategoryDTO toDto(BusinessCategory businessCategory);

    @Mapping(target = "businessCategories", ignore = true)
    @Mapping(target = "removeBusinessCategory", ignore = true)
    @Mapping(target = "documentTypeBusinessCategories", ignore = true)
    @Mapping(target = "removeDocumentTypeBusinessCategory", ignore = true)
    @Mapping(source = "parentCategoryId", target = "parentCategory")
    @Mapping(target = "vendors", ignore = true)
    @Mapping(target = "removeVendor", ignore = true)
    BusinessCategory toEntity(BusinessCategoryDTO businessCategoryDTO);

    default BusinessCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        BusinessCategory businessCategory = new BusinessCategory();
        businessCategory.setId(id);
        return businessCategory;
    }
}
