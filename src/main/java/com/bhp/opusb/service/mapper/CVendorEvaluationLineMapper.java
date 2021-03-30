package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CVendorEvaluationLine;
import com.bhp.opusb.service.dto.CVendorEvaluationLineDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CVendorEvaluationLine} and its DTO {@link CVendorEvaluationLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CQuestionCategoryMapper.class, CVendorEvaluationMapper.class})
public interface CVendorEvaluationLineMapper extends EntityMapper<CVendorEvaluationLineDTO, CVendorEvaluationLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "CQuestionCategory.id", target = "CQuestionCategoryId")
    @Mapping(source = "CQuestionCategory.name", target = "CQuestionCategoryName")
    @Mapping(source = "CVendorEvaluation.id", target = "CVendorEvaluationId")
    @Mapping(source = "CVendorEvaluation.name", target = "CVendorEvaluationName")
    CVendorEvaluationLineDTO toDto(CVendorEvaluationLine cVendorEvaluationLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "CQuestionCategoryId", target = "CQuestionCategory")
    @Mapping(source = "CVendorEvaluationId", target = "CVendorEvaluation")
    CVendorEvaluationLine toEntity(CVendorEvaluationLineDTO cVendorEvaluationLineDTO);

    default CVendorEvaluationLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CVendorEvaluationLine cVendorEvaluationLine = new CVendorEvaluationLine();
        cVendorEvaluationLine.setId(id);
        return cVendorEvaluationLine;
    }
}
