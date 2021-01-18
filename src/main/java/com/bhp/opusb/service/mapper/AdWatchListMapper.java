package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.AdWatchList;
import com.bhp.opusb.service.dto.AdWatchListDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AdWatchList} and its DTO {@link AdWatchListDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface AdWatchListMapper extends EntityMapper<AdWatchListDTO, AdWatchList> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    AdWatchListDTO toDto(AdWatchList adWatchList);

    @Mapping(target = "adWatchListItems", ignore = true)
    @Mapping(target = "removeAdWatchListItem", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    AdWatchList toEntity(AdWatchListDTO adWatchListDTO);

    default AdWatchList fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdWatchList adWatchList = new AdWatchList();
        adWatchList.setId(id);
        return adWatchList;
    }
}
