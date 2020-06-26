package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.AdMenu;
import com.bhp.opusb.service.dto.AdMenuDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AdMenu} and its DTO {@link AdMenuDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADWindowMapper.class, ADOrganizationMapper.class})
public interface AdMenuMapper extends EntityMapper<AdMenuDTO, AdMenu> {

    @Mapping(source = "adWindow.id", target = "adWindowId")
    @Mapping(source = "adWindow.name", target = "adWindowName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "parentMenu.id", target = "parentMenuId")
    @Mapping(source = "parentMenu.name", target = "parentMenuName")
    AdMenuDTO toDto(AdMenu adMenu);

    @Mapping(target = "adMenus", ignore = true)
    @Mapping(target = "removeAdMenu", ignore = true)
    @Mapping(source = "adWindowId", target = "adWindow")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "parentMenuId", target = "parentMenu")
    AdMenu toEntity(AdMenuDTO adMenuDTO);

    default AdMenu fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdMenu adMenu = new AdMenu();
        adMenu.setId(id);
        return adMenu;
    }
}
