package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CRegion;
import com.bhp.opusb.service.dto.CRegionDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CRegion} and its DTO {@link CRegionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CCountryMapper.class})
public interface CRegionMapper extends EntityMapper<CRegionDTO, CRegion> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    CRegionDTO toDto(CRegion cRegion);

    @Mapping(target = "cCities", ignore = true)
    @Mapping(target = "removeCCity", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "countryId", target = "country")
    CRegion toEntity(CRegionDTO cRegionDTO);

    default CRegion fromId(Long id) {
        if (id == null) {
            return null;
        }
        CRegion cRegion = new CRegion();
        cRegion.setId(id);
        return cRegion;
    }
}
