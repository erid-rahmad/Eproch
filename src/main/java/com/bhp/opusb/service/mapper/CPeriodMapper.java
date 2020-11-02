package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPeriodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPeriod} and its DTO {@link CPeriodDTO}.
 */
@Mapper(componentModel = "spring", uses = {CCalendarMapper.class, ADOrganizationMapper.class})
public interface CPeriodMapper extends EntityMapper<CPeriodDTO, CPeriod> {

    @Mapping(source = "calendar.id", target = "calendarId")
    @Mapping(source = "calendar.name", target = "calendarName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CPeriodDTO toDto(CPeriod cPeriod);

    @Mapping(source = "calendarId", target = "calendar")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CPeriod toEntity(CPeriodDTO cPeriodDTO);

    default CPeriod fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPeriod cPeriod = new CPeriod();
        cPeriod.setId(id);
        return cPeriod;
    }
}
