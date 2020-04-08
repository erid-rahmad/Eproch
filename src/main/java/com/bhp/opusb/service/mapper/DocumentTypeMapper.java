package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.DocumentTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentType} and its DTO {@link DocumentTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentTypeMapper extends EntityMapper<DocumentTypeDTO, DocumentType> {


    @Mapping(target = "removeDocumentTypeBusinessCategory", ignore = true)
    DocumentType toEntity(DocumentTypeDTO documentTypeDTO);

    default DocumentType fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentType documentType = new DocumentType();
        documentType.setId(id);
        return documentType;
    }
}
