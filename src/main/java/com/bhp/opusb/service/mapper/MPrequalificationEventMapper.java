package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalificationEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalificationEvent} and its DTO {@link MPrequalificationEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {MPrequalificationInformationMapper.class, ADOrganizationMapper.class, CPrequalificationEventMapper.class, CPrequalificationMethodMapper.class})
public interface MPrequalificationEventMapper extends EntityMapper<MPrequalificationEventDTO, MPrequalificationEvent> {

    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "method.id", target = "methodId")
    MPrequalificationEventDTO toDto(MPrequalificationEvent mPrequalificationEvent);

    @Mapping(source = "prequalificationId", target = "prequalification")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "eventId", target = "event")
    @Mapping(source = "methodId", target = "method")
    MPrequalificationEvent toEntity(MPrequalificationEventDTO mPrequalificationEventDTO);

    default MPrequalificationEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalificationEvent mPrequalificationEvent = new MPrequalificationEvent();
        mPrequalificationEvent.setId(id);
        return mPrequalificationEvent;
    }
}
