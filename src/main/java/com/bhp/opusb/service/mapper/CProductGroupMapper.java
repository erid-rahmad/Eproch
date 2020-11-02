package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CProductGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CProductGroup} and its DTO {@link CProductGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CProductGroupMapper extends EntityMapper<CProductGroupDTO, CProductGroup> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CProductGroupDTO toDto(CProductGroup cProductGroup);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CProductGroup toEntity(CProductGroupDTO cProductGroupDTO);

    default CProductGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        CProductGroup cProductGroup = new CProductGroup();
        cProductGroup.setId(id);
        return cProductGroup;
    }
}
