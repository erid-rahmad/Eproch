package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.RegionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class})
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    RegionDTO toDto(Region region);

    @Mapping(target = "cities", ignore = true)
    @Mapping(target = "removeCity", ignore = true)
    @Mapping(source = "countryId", target = "country")
    Region toEntity(RegionDTO regionDTO);

    default Region fromId(Long id) {
        if (id == null) {
            return null;
        }
        Region region = new Region();
        region.setId(id);
        return region;
    }
}
