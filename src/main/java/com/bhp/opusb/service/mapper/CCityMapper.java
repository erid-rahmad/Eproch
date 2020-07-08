package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CCityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CCity} and its DTO {@link CCityDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CCountryMapper.class, CRegionMapper.class})
public interface CCityMapper extends EntityMapper<CCityDTO, CCity> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "region.id", target = "regionId")
    @Mapping(source = "region.name", target = "regionName")
    CCityDTO toDto(CCity cCity);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "countryId", target = "country")
    @Mapping(source = "regionId", target = "region")
    CCity toEntity(CCityDTO cCityDTO);

    default CCity fromId(Long id) {
        if (id == null) {
            return null;
        }
        CCity cCity = new CCity();
        cCity.setId(id);
        return cCity;
    }
}
