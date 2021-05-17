package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPrequalificationEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPrequalificationEvent} and its DTO {@link CPrequalificationEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CPrequalificationEventMapper extends EntityMapper<CPrequalificationEventDTO, CPrequalificationEvent> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CPrequalificationEventDTO toDto(CPrequalificationEvent cPrequalificationEvent);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CPrequalificationEvent toEntity(CPrequalificationEventDTO cPrequalificationEventDTO);

    default CPrequalificationEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPrequalificationEvent cPrequalificationEvent = new CPrequalificationEvent();
        cPrequalificationEvent.setId(id);
        return cPrequalificationEvent;
    }
}
