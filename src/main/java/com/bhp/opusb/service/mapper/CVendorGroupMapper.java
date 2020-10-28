package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CVendorGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CVendorGroup} and its DTO {@link CVendorGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CVendorGroupMapper extends EntityMapper<CVendorGroupDTO, CVendorGroup> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CVendorGroupDTO toDto(CVendorGroup cVendorGroup);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CVendorGroup toEntity(CVendorGroupDTO cVendorGroupDTO);

    default CVendorGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        CVendorGroup cVendorGroup = new CVendorGroup();
        cVendorGroup.setId(id);
        return cVendorGroup;
    }
}
