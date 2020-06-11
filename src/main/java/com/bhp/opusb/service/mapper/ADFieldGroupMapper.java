package com.bhp.opusb.service.mapper;

import com.bhp.opusb.domain.ADFieldGroup;
import com.bhp.opusb.service.dto.ADFieldGroupDTO;

import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ADFieldGroup} and its DTO
 * {@link ADFieldGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ADFieldGroupMapper extends EntityMapper<ADFieldGroupDTO, ADFieldGroup> {

    default ADFieldGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADFieldGroup aDFieldGroup = new ADFieldGroup();
        aDFieldGroup.setId(id);
        return aDFieldGroup;
    }
}
