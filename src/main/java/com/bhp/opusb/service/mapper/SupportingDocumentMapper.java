package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.SupportingDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupportingDocument} and its DTO {@link SupportingDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocumentTypeMapper.class, VendorMapper.class})
public interface SupportingDocumentMapper extends EntityMapper<SupportingDocumentDTO, SupportingDocument> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "vendor.id", target = "vendorId")
    SupportingDocumentDTO toDto(SupportingDocument supportingDocument);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "vendorId", target = "vendor")
    SupportingDocument toEntity(SupportingDocumentDTO supportingDocumentDTO);

    default SupportingDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupportingDocument supportingDocument = new SupportingDocument();
        supportingDocument.setId(id);
        return supportingDocument;
    }
}
