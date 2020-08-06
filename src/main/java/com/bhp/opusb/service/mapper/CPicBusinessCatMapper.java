package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPicBusinessCatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPicBusinessCat} and its DTO {@link CPicBusinessCatDTO}.
 */
@Mapper(componentModel = "spring", uses = {CPersonInChargeMapper.class, CBusinessCategoryMapper.class, ADOrganizationMapper.class})
public interface CPicBusinessCatMapper extends EntityMapper<CPicBusinessCatDTO, CPicBusinessCat> {

    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CPicBusinessCatDTO toDto(CPicBusinessCat cPicBusinessCat);

    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CPicBusinessCat toEntity(CPicBusinessCatDTO cPicBusinessCatDTO);

    default CPicBusinessCat fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPicBusinessCat cPicBusinessCat = new CPicBusinessCat();
        cPicBusinessCat.setId(id);
        return cPicBusinessCat;
    }
}
