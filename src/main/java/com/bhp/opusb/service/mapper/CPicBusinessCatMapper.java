package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CPicBusinessCat;
import com.bhp.opusb.service.dto.CPicBusinessCatDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CPicBusinessCat} and its DTO {@link CPicBusinessCatDTO}.
 */
@Mapper(componentModel = "spring", uses = {AdUserMapper.class, CBusinessCategoryMapper.class, ADOrganizationMapper.class})
public interface CPicBusinessCatMapper extends EntityMapper<CPicBusinessCatDTO, CPicBusinessCat> {

    @Mapping(source = "pic.id", target = "picId")
    @Mapping(source = "pic.user.login", target = "picUserLogin")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "businessCategory.name", target = "businessCategoryName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CPicBusinessCatDTO toDto(CPicBusinessCat cPicBusinessCat);

    @Mapping(source = "picId", target = "pic")
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
