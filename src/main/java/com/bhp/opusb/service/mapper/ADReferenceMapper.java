package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.ADReference;
import com.bhp.opusb.service.dto.ADReferenceDTO;
import com.bhp.opusb.service.dto.ADReferenceListDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ADReference} and its DTO {@link ADReferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = { ADReferenceListDTO.class, ADOrganizationMapper.class})
public interface ADReferenceMapper extends EntityMapper<ADReferenceDTO, ADReference> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    ADReferenceDTO toDto(ADReference aDReference);

    @Mapping(target = "aDReferenceLists", ignore = true)
    @Mapping(target = "removeADReferenceList", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
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
