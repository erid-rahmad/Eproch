package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CGalleryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CGallery} and its DTO {@link CGalleryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CGalleryMapper extends EntityMapper<CGalleryDTO, CGallery> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CGalleryDTO toDto(CGallery cGallery);

    @Mapping(target = "cGalleryItems", ignore = true)
    @Mapping(target = "removeCGalleryItem", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CGallery toEntity(CGalleryDTO cGalleryDTO);

    default CGallery fromId(Long id) {
        if (id == null) {
            return null;
        }
        CGallery cGallery = new CGallery();
        cGallery.setId(id);
        return cGallery;
    }
}
