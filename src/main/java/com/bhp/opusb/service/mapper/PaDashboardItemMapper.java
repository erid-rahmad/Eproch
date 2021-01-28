package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.PaDashboardItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaDashboardItem} and its DTO {@link PaDashboardItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdWatchListMapper.class, PaDashboardMapper.class})
public interface PaDashboardItemMapper extends EntityMapper<PaDashboardItemDTO, PaDashboardItem> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adWatchList.id", target = "adWatchListId")
    @Mapping(source = "adWatchList.name", target = "adWatchListName")
    @Mapping(source = "paDashboard.id", target = "paDashboardId")
    @Mapping(source = "paDashboard.name", target = "paDashboardName")
    PaDashboardItemDTO toDto(PaDashboardItem paDashboardItem);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adWatchListId", target = "adWatchList")
    @Mapping(source = "paDashboardId", target = "paDashboard")
    PaDashboardItem toEntity(PaDashboardItemDTO paDashboardItemDTO);

    default PaDashboardItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaDashboardItem paDashboardItem = new PaDashboardItem();
        paDashboardItem.setId(id);
        return paDashboardItem;
    }
}
