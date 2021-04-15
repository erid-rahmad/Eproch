package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CEvent} and its DTO {@link CEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CProductClassificationMapper.class})
public interface CEventMapper extends EntityMapper<CEventDTO, CEvent> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "CProductClassification.id", target = "cProductClassificationId")
    @Mapping(source = "CProductClassification.name", target = "cProductClassificationName")
    CEventDTO toDto(CEvent cEvent);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "cProductClassificationId", target = "cProductClassification")
    CEvent toEntity(CEventDTO cEventDTO);

    default CEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEvent cEvent = new CEvent();
        cEvent.setId(id);
        return cEvent;
    }
}
