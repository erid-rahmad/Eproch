package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.ADOrganizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ADOrganization} and its DTO {@link ADOrganizationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ADOrganizationMapper extends EntityMapper<ADOrganizationDTO, ADOrganization> {



    default ADOrganization fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADOrganization aDOrganization = new ADOrganization();
        aDOrganization.setId(id);
        return aDOrganization;
    }
}
