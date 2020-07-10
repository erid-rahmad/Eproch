package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CRegistrationDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CRegistrationDocument} and its DTO {@link CRegistrationDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CRegistrationDocTypeMapper.class, CAttachmentMapper.class, CVendorMapper.class, ADOrganizationMapper.class})
public interface CRegistrationDocumentMapper extends EntityMapper<CRegistrationDocumentDTO, CRegistrationDocument> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "file.id", target = "fileId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CRegistrationDocumentDTO toDto(CRegistrationDocument cRegistrationDocument);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "fileId", target = "file")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CRegistrationDocument toEntity(CRegistrationDocumentDTO cRegistrationDocumentDTO);

    default CRegistrationDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        CRegistrationDocument cRegistrationDocument = new CRegistrationDocument();
        cRegistrationDocument.setId(id);
        return cRegistrationDocument;
    }
}
