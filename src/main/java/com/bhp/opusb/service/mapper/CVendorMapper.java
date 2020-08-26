package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CVendorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CVendor} and its DTO {@link CVendorDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, ADOrganizationMapper.class})
public interface CVendorMapper extends EntityMapper<CVendorDTO, CVendor> {

    @Mapping(source = "taxIdFile.id", target = "taxIdFileId")
    @Mapping(source = "taxIdFile.fileName", target = "taxIdFileName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CVendorDTO toDto(CVendor cVendor);

    @Mapping(source = "taxIdFileId", target = "taxIdFile")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CVendor toEntity(CVendorDTO cVendorDTO);

    default CVendor fromId(Long id) {
        if (id == null) {
            return null;
        }
        CVendor cVendor = new CVendor();
        cVendor.setId(id);
        return cVendor;
    }
}
