package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPreqRegistEvaluationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPreqRegistEvaluation} and its DTO {@link MPreqRegistEvaluationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CVendorMapper.class, MPrequalificationInformationMapper.class})
public interface MPreqRegistEvaluationMapper extends EntityMapper<MPreqRegistEvaluationDTO, MPreqRegistEvaluation> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "prequalification.id", target = "prequalificationId")
    MPreqRegistEvaluationDTO toDto(MPreqRegistEvaluation mPreqRegistEvaluation);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "prequalificationId", target = "prequalification")
    MPreqRegistEvaluation toEntity(MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO);

    default MPreqRegistEvaluation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPreqRegistEvaluation mPreqRegistEvaluation = new MPreqRegistEvaluation();
        mPreqRegistEvaluation.setId(id);
        return mPreqRegistEvaluation;
    }
}
