package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CQuestionCategory;
import com.bhp.opusb.service.dto.CQuestionCategoryDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CQuestionCategory} and its DTO {@link CQuestionCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CQuestionCategoryMapper extends EntityMapper<CQuestionCategoryDTO, CQuestionCategory> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CQuestionCategoryDTO toDto(CQuestionCategory cQuestionCategory);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CQuestionCategory toEntity(CQuestionCategoryDTO cQuestionCategoryDTO);

    default CQuestionCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        CQuestionCategory cQuestionCategory = new CQuestionCategory();
        cQuestionCategory.setId(id);
        return cQuestionCategory;
    }
}
