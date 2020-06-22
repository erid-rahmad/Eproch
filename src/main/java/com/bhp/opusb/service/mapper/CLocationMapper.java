package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CLocation} and its DTO {@link CLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CCityMapper.class})
public interface CLocationMapper extends EntityMapper<CLocationDTO, CLocation> {

    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    CLocationDTO toDto(CLocation cLocation);

    @Mapping(source = "cityId", target = "city")
    CLocation toEntity(CLocationDTO cLocationDTO);

    default CLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        CLocation cLocation = new CLocation();
        cLocation.setId(id);
        return cLocation;
    }
}
