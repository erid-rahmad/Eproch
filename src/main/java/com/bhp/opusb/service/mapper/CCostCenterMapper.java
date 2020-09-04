package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CCostCenterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CCostCenter} and its DTO {@link CCostCenterDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CCostCenterMapper extends EntityMapper<CCostCenterDTO, CCostCenter> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CCostCenterDTO toDto(CCostCenter cCostCenter);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CCostCenter toEntity(CCostCenterDTO cCostCenterDTO);

    default CCostCenter fromId(Long id) {
        if (id == null) {
            return null;
        }
        CCostCenter cCostCenter = new CCostCenter();
        cCostCenter.setId(id);
        return cCostCenter;
    }
}
