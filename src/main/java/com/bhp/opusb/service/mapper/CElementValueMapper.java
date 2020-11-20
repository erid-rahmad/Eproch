package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CElementValue;
import com.bhp.opusb.service.dto.CElementValueDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CElementValue} and its DTO {@link CElementValueDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CElementValueMapper extends EntityMapper<CElementValueDTO, CElementValue> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CElementValueDTO toDto(CElementValue cElementValue);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CElementValue toEntity(CElementValueDTO cElementValueDTO);

    default CElementValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        CElementValue cElementValue = new CElementValue();
        cElementValue.setId(id);
        return cElementValue;
    }
}
