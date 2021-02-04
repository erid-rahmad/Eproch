package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.AdWatchListItem;
import com.bhp.opusb.service.dto.AdWatchListItemDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AdWatchListItem} and its DTO {@link AdWatchListItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdMenuMapper.class, AdWatchListMapper.class})
public interface AdWatchListItemMapper extends EntityMapper<AdWatchListItemDTO, AdWatchListItem> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adMenu.id", target = "adMenuId")
    @Mapping(source = "adMenu.name", target = "adMenuName")
    @Mapping(source = "adWatchList.id", target = "adWatchListId")
    @Mapping(source = "adWatchList.name", target = "adWatchListName")
    AdWatchListItemDTO toDto(AdWatchListItem adWatchListItem);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adMenuId", target = "adMenu")
    @Mapping(source = "adWatchListId", target = "adWatchList")
    AdWatchListItem toEntity(AdWatchListItemDTO adWatchListItemDTO);

    default AdWatchListItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdWatchListItem adWatchListItem = new AdWatchListItem();
        adWatchListItem.setId(id);
        return adWatchListItem;
    }
}
