package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CVendorLocation;
import com.bhp.opusb.service.dto.CVendorLocationDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CVendorLocation} and its DTO {@link CVendorLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CVendorMapper.class, CLocationMapper.class, ADOrganizationMapper.class})
public interface CVendorLocationMapper extends EntityMapper<CVendorLocationDTO, CVendorLocation> {

    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.code", target = "vendorCode")
    @Mapping(source = "vendor.name", target = "vendorRegisteredName")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.address1", target = "address1")
    @Mapping(source = "location.address2", target = "address2")
    @Mapping(source = "location.address3", target = "address3")
    @Mapping(source = "location.address4", target = "address4")
    @Mapping(source = "location.postalCode", target = "postalCode")
    @Mapping(source = "location.phone", target = "phone")
    @Mapping(source = "location.fax", target = "fax")
    @Mapping(source = "location.city.name", target = "cityName")
    @Mapping(source = "location.city.region.name", target = "regionName")
    @Mapping(source = "location.city.region.country.name", target = "countryWithRegionName")
    @Mapping(source = "location.city.country.name", target = "countryWithoutRegionName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CVendorLocationDTO toDto(CVendorLocation cVendorLocation);

    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CVendorLocation toEntity(CVendorLocationDTO cVendorLocationDTO);

    default CVendorLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        CVendorLocation cVendorLocation = new CVendorLocation();
        cVendorLocation.setId(id);
        return cVendorLocation;
    }
}
