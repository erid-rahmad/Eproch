package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CCalendarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CCalendar} and its DTO {@link CCalendarDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CCalendarMapper extends EntityMapper<CCalendarDTO, CCalendar> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CCalendarDTO toDto(CCalendar cCalendar);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CCalendar toEntity(CCalendarDTO cCalendarDTO);

    default CCalendar fromId(Long id) {
        if (id == null) {
            return null;
        }
        CCalendar cCalendar = new CCalendar();
        cCalendar.setId(id);
        return cCalendar;
    }
}
