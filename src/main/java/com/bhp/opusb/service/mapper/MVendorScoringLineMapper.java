package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVendorScoringLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVendorScoringLine} and its DTO {@link MVendorScoringLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CEvaluationMethodLineMapper.class, MVendorScoringMapper.class})
public interface MVendorScoringLineMapper extends EntityMapper<MVendorScoringLineDTO, MVendorScoringLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "evaluationMethodLine.id", target = "evaluationMethodLineId")
    @Mapping(source = "evaluationMethodLine.evaluationType", target = "evaluationMethodLineEvaluationType")
    @Mapping(source = "evaluationMethodLine.weight", target = "evaluationMethodLineWeight")
    @Mapping(source = "evaluationMethodLine.passingGrade", target = "evaluationMethodLinePassingGrade")
    @Mapping(source = "evaluationMethodLine.evaluation", target = "evaluationMethodLineEvaluation")
    @Mapping(source = "vendorScoring.id", target = "vendorScoringId")
    MVendorScoringLineDTO toDto(MVendorScoringLine mVendorScoringLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "evaluationMethodLineId", target = "evaluationMethodLine")
    @Mapping(source = "vendorScoringId", target = "vendorScoring")
    MVendorScoringLine toEntity(MVendorScoringLineDTO mVendorScoringLineDTO);

    default MVendorScoringLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorScoringLine mVendorScoringLine = new MVendorScoringLine();
        mVendorScoringLine.setId(id);
        return mVendorScoringLine;
    }
}
