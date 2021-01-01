package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CGalleryItem;
import com.bhp.opusb.service.dto.CGalleryItemDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CGalleryItem} and its DTO {@link CGalleryItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, ADOrganizationMapper.class, CGalleryMapper.class})
public interface CGalleryItemMapper extends EntityMapper<CGalleryItemDTO, CGalleryItem> {

    @Mapping(source = "CAttachment.id", target = "CAttachmentId")
    @Mapping(source = "CAttachment.fileName", target = "CAttachmentName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "CGallery.id", target = "CGalleryId")
    @Mapping(source = "CGallery.name", target = "CGalleryName")
    CGalleryItemDTO toDto(CGalleryItem cGalleryItem);

    @Mapping(source = "CAttachmentId", target = "CAttachment")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "CGalleryId", target = "CGallery")
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
