package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.ADReferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ADReference} and its DTO {@link ADReferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ADReferenceMapper extends EntityMapper<ADReferenceDTO, ADReference> {


    @Mapping(target = "aDReferenceLists", ignore = true)
    @Mapping(target = "removeADReferenceList", ignore = true)
    ADReference toEntity(ADReferenceDTO aDReferenceDTO);

    default ADReference fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADReference aDReference = new ADReference();
        aDReference.setId(id);
        return aDReference;
    }
}
