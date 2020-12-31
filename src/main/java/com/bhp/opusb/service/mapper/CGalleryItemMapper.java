package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CGalleryItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CGalleryItem} and its DTO {@link CGalleryItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, ADOrganizationMapper.class, CGalleryMapper.class})
public interface CGalleryItemMapper extends EntityMapper<CGalleryItemDTO, CGalleryItem> {

    @Mapping(source = "cAttachment.id", target = "cAttachmentId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "cGallery.id", target = "cGalleryId")
    CGalleryItemDTO toDto(CGalleryItem cGalleryItem);

    @Mapping(source = "cAttachmentId", target = "cAttachment")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "cGalleryId", target = "cGallery")
    CGalleryItem toEntity(CGalleryItemDTO cGalleryItemDTO);

    default CGalleryItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        CGalleryItem cGalleryItem = new CGalleryItem();
        cGalleryItem.setId(id);
        return cGalleryItem;
    }
}
