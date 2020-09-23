package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.COrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link COrganization} and its DTO {@link COrganizationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface COrganizationMapper extends EntityMapper<COrganizationDTO, COrganization> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    COrganizationDTO toDto(COrganization cOrganization);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    COrganization toEntity(COrganizationDTO cOrganizationDTO);

    default COrganization fromId(Long id) {
        if (id == null) {
            return null;
        }
        COrganization cOrganization = new COrganization();
        cOrganization.setId(id);
        return cOrganization;
    }
}
