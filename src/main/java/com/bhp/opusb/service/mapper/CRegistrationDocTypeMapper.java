package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CRegistrationDocTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CRegistrationDocType} and its DTO {@link CRegistrationDocTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CRegistrationDocTypeMapper extends EntityMapper<CRegistrationDocTypeDTO, CRegistrationDocType> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CRegistrationDocTypeDTO toDto(CRegistrationDocType cRegistrationDocType);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CRegistrationDocType toEntity(CRegistrationDocTypeDTO cRegistrationDocTypeDTO);

    default CRegistrationDocType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CRegistrationDocType cRegistrationDocType = new CRegistrationDocType();
        cRegistrationDocType.setId(id);
        return cRegistrationDocType;
    }
}
