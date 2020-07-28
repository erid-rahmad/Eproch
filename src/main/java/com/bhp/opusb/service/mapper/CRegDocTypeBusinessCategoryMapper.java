package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CRegDocTypeBusinessCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CRegDocTypeBusinessCategory} and its DTO {@link CRegDocTypeBusinessCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {CRegistrationDocTypeMapper.class, CBusinessCategoryMapper.class, ADOrganizationMapper.class})
public interface CRegDocTypeBusinessCategoryMapper extends EntityMapper<CRegDocTypeBusinessCategoryDTO, CRegDocTypeBusinessCategory> {

    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "businessCategory.name", target = "businessCategoryName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CRegDocTypeBusinessCategoryDTO toDto(CRegDocTypeBusinessCategory cRegDocTypeBusinessCategory);

    @Mapping(source = "documentTypeId", target = "documentType")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CRegDocTypeBusinessCategory toEntity(CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO);

    default CRegDocTypeBusinessCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        CRegDocTypeBusinessCategory cRegDocTypeBusinessCategory = new CRegDocTypeBusinessCategory();
        cRegDocTypeBusinessCategory.setId(id);
        return cRegDocTypeBusinessCategory;
    }
}
