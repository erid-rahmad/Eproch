package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CNonBusinessDayDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CNonBusinessDay} and its DTO {@link CNonBusinessDayDTO}.
 */
@Mapper(componentModel = "spring", uses = {CCalendarMapper.class, ADOrganizationMapper.class})
public interface CNonBusinessDayMapper extends EntityMapper<CNonBusinessDayDTO, CNonBusinessDay> {

    @Mapping(source = "calendar.id", target = "calendarId")
    @Mapping(source = "calendar.name", target = "calendarName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CNonBusinessDayDTO toDto(CNonBusinessDay cNonBusinessDay);

    @Mapping(source = "calendarId", target = "calendar")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CNonBusinessDay toEntity(CNonBusinessDayDTO cNonBusinessDayDTO);

    default CNonBusinessDay fromId(Long id) {
        if (id == null) {
            return null;
        }
        CNonBusinessDay cNonBusinessDay = new CNonBusinessDay();
        cNonBusinessDay.setId(id);
        return cNonBusinessDay;
    }
}
