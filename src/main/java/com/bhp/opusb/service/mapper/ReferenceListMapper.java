package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.ReferenceListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReferenceList} and its DTO {@link ReferenceListDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReferenceMapper.class})
public interface ReferenceListMapper extends EntityMapper<ReferenceListDTO, ReferenceList> {

    @Mapping(source = "reference.id", target = "referenceId")
    ReferenceListDTO toDto(ReferenceList referenceList);

    @Mapping(source = "referenceId", target = "reference")
    ReferenceList toEntity(ReferenceListDTO referenceListDTO);

    default ReferenceList fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReferenceList referenceList = new ReferenceList();
        referenceList.setId(id);
        return referenceList;
    }
}
