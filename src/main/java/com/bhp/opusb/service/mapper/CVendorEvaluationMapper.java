package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CVendorEvaluation;
import com.bhp.opusb.service.dto.CVendorEvaluationDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CVendorEvaluation} and its DTO {@link CVendorEvaluationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CVendorEvaluationMapper extends EntityMapper<CVendorEvaluationDTO, CVendorEvaluation> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CVendorEvaluationDTO toDto(CVendorEvaluation cVendorEvaluation);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CVendorEvaluation toEntity(CVendorEvaluationDTO cVendorEvaluationDTO);

    default CVendorEvaluation fromId(Long id) {
        if (id == null) {
            return null;
        }
        CVendorEvaluation cVendorEvaluation = new CVendorEvaluation();
        cVendorEvaluation.setId(id);
        return cVendorEvaluation;
    }
}
