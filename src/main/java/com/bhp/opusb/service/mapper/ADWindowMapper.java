package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.ADWindowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ADWindow} and its DTO {@link ADWindowDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface ADWindowMapper extends EntityMapper<ADWindowDTO, ADWindow> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    ADWindowDTO toDto(ADWindow aDWindow);

    @Mapping(target = "aDTabs", ignore = true)
    @Mapping(target = "removeADTab", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    ADWindow toEntity(ADWindowDTO aDWindowDTO);

    default ADWindow fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADWindow aDWindow = new ADWindow();
        aDWindow.setId(id);
        return aDWindow;
    }
}
