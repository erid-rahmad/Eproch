package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CConvertionTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CConvertionType} and its DTO {@link CConvertionTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CConvertionTypeMapper extends EntityMapper<CConvertionTypeDTO, CConvertionType> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CConvertionTypeDTO toDto(CConvertionType cConvertionType);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CConvertionType toEntity(CConvertionTypeDTO cConvertionTypeDTO);

    default CConvertionType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CConvertionType cConvertionType = new CConvertionType();
        cConvertionType.setId(id);
        return cConvertionType;
    }
}
