package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPreqRegistDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPreqRegistDocument} and its DTO {@link MPreqRegistDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MPrequalRegistrationMapper.class, CAttachmentMapper.class})
public interface MPreqRegistDocumentMapper extends EntityMapper<MPreqRegistDocumentDTO, MPreqRegistDocument> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "registration.id", target = "registrationId")
    @Mapping(source = "siupDocument.id", target = "siupDocumentId")
    @Mapping(source = "spdaDocument.id", target = "spdaDocumentId")
    MPreqRegistDocumentDTO toDto(MPreqRegistDocument mPreqRegistDocument);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "registrationId", target = "registration")
    @Mapping(source = "siupDocumentId", target = "siupDocument")
    @Mapping(source = "spdaDocumentId", target = "spdaDocument")
    MPreqRegistDocument toEntity(MPreqRegistDocumentDTO mPreqRegistDocumentDTO);

    default MPreqRegistDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPreqRegistDocument mPreqRegistDocument = new MPreqRegistDocument();
        mPreqRegistDocument.setId(id);
        return mPreqRegistDocument;
    }
}
