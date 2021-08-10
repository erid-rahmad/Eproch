package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPrequalificationStepDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPrequalificationStep} and its DTO {@link CPrequalificationStepDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdFormMapper.class})
public interface CPrequalificationStepMapper extends EntityMapper<CPrequalificationStepDTO, CPrequalificationStep> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adForm.id", target = "adFormId")
    @Mapping(source = "adForm.name", target = "adFormName")
    CPrequalificationStepDTO toDto(CPrequalificationStep cPrequalificationStep);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adFormId", target = "adForm")
    CPrequalificationStep toEntity(CPrequalificationStepDTO cPrequalificationStepDTO);

    default CPrequalificationStep fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPrequalificationStep cPrequalificationStep = new CPrequalificationStep();
        cPrequalificationStep.setId(id);
        return cPrequalificationStep;
    }
}
