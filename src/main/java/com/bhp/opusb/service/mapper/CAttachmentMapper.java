package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CAttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CAttachment} and its DTO {@link CAttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CAttachmentMapper extends EntityMapper<CAttachmentDTO, CAttachment> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CAttachmentDTO toDto(CAttachment cAttachment);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CAttachment toEntity(CAttachmentDTO cAttachmentDTO);

    default CAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        CAttachment cAttachment = new CAttachment();
        cAttachment.setId(id);
        return cAttachment;
    }
}
