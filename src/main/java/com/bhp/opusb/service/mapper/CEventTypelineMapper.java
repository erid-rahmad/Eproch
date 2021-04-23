package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CEventTypeline;
import com.bhp.opusb.service.dto.CEventTypelineDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CEventTypeline} and its DTO {@link CEventTypelineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CEventTypeMapper.class, CEventMapper.class})
public interface CEventTypelineMapper extends EntityMapper<CEventTypelineDTO, CEventTypeline> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "eventType.id", target = "eventTypeId")
    @Mapping(source = "eventType.name", target = "eventTypeName")
    @Mapping(source = "CEvent.id", target = "CEventId")
    @Mapping(source = "CEvent.name", target = "CEventName")
    CEventTypelineDTO toDto(CEventTypeline cEventTypeline);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "eventTypeId", target = "eventType")
    @Mapping(source = "CEventId", target = "CEvent")
    CEventTypeline toEntity(CEventTypelineDTO cEventTypelineDTO);

    default CEventTypeline fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEventTypeline cEventTypeline = new CEventTypeline();
        cEventTypeline.setId(id);
        return cEventTypeline;
    }
}
