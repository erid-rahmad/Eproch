package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.ADTabDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ADTab} and its DTO {@link ADTabDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADClientMapper.class, ADOrganizationMapper.class, ADTableMapper.class, ADWindowMapper.class})
public interface ADTabMapper extends EntityMapper<ADTabDTO, ADTab> {

    @Mapping(source = "adClient.id", target = "adClientId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adTable.id", target = "adTableId")
    @Mapping(source = "adWindow.id", target = "adWindowId")
    ADTabDTO toDto(ADTab aDTab);

    @Mapping(source = "adClientId", target = "adClient")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adTableId", target = "adTable")
    @Mapping(source = "adWindowId", target = "adWindow")
    ADTab toEntity(ADTabDTO aDTabDTO);

    default ADTab fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADTab aDTab = new ADTab();
        aDTab.setId(id);
        return aDTab;
    }
}
