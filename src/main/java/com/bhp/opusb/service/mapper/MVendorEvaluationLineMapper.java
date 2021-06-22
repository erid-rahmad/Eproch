package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MVendorEvaluationLine;
import com.bhp.opusb.service.dto.MVendorEvaluationLineDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MVendorEvaluationLine} and its DTO {@link MVendorEvaluationLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MVendorEvaluationMapper.class, CVendorEvaluationLineMapper.class})
public interface MVendorEvaluationLineMapper extends EntityMapper<MVendorEvaluationLineDTO, MVendorEvaluationLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "vendorEvaluation.id", target = "vendorEvaluationId")
    @Mapping(source = "vendorEvaluation.documentNo", target = "vendorEvaluationName")
    @Mapping(source = "evaluationLine.id", target = "evaluationLineId")
    @Mapping(source = "evaluationLine.CQuestionCategory.name", target = "evaluationLineName")
    @Mapping(source = "evaluationLine.question", target = "evaluationLineQuestion")
    MVendorEvaluationLineDTO toDto(MVendorEvaluationLine mVendorEvaluationLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "vendorEvaluationId", target = "vendorEvaluation")
    @Mapping(source = "evaluationLineId", target = "evaluationLine")
    MVendorEvaluationLine toEntity(MVendorEvaluationLineDTO mVendorEvaluationLineDTO);

    default MVendorEvaluationLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorEvaluationLine mVendorEvaluationLine = new MVendorEvaluationLine();
        mVendorEvaluationLine.setId(id);
        return mVendorEvaluationLine;
    }
}
