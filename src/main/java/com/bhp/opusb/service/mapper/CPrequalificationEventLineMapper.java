package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPrequalificationEventLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPrequalificationEventLine} and its DTO {@link CPrequalificationEventLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {CPrequalificationEventMapper.class, CPrequalificationStepMapper.class, ADOrganizationMapper.class})
public interface CPrequalificationEventLineMapper extends EntityMapper<CPrequalificationEventLineDTO, CPrequalificationEventLine> {

    @Mapping(source = "prequalificationEvent.id", target = "prequalificationEventId")
    @Mapping(source = "prequalificationStep.id", target = "prequalificationStepId")
    @Mapping(source = "prequalificationStep.name", target = "prequalificationStepName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CPrequalificationEventLineDTO toDto(CPrequalificationEventLine cPrequalificationEventLine);

    @Mapping(source = "prequalificationEventId", target = "prequalificationEvent")
    @Mapping(source = "prequalificationStepId", target = "prequalificationStep")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CPrequalificationEventLine toEntity(CPrequalificationEventLineDTO cPrequalificationEventLineDTO);

    default CPrequalificationEventLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPrequalificationEventLine cPrequalificationEventLine = new CPrequalificationEventLine();
        cPrequalificationEventLine.setId(id);
        return cPrequalificationEventLine;
    }
}
