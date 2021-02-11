package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CEventTypelineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CEventTypeline} and its DTO {@link CEventTypelineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CEventTypeMapper.class})
public interface CEventTypelineMapper extends EntityMapper<CEventTypelineDTO, CEventTypeline> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "eventType.id", target = "eventTypeId")
    @Mapping(source = "eventType.name", target = "eventTypeName")
    CEventTypelineDTO toDto(CEventTypeline cEventTypeline);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "eventTypeId", target = "eventType")
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
