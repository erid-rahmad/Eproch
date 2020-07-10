package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CVendorLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CVendorLocation} and its DTO {@link CVendorLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CVendorMapper.class, CLocationMapper.class, ADOrganizationMapper.class})
public interface CVendorLocationMapper extends EntityMapper<CVendorLocationDTO, CVendorLocation> {

    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
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
