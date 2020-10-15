package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.ADOrganizationInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ADOrganizationInfo} and its DTO {@link ADOrganizationInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface ADOrganizationInfoMapper extends EntityMapper<ADOrganizationInfoDTO, ADOrganizationInfo> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    ADOrganizationInfoDTO toDto(ADOrganizationInfo aDOrganizationInfo);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    ADOrganizationInfo toEntity(ADOrganizationInfoDTO aDOrganizationInfoDTO);

    default ADOrganizationInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADOrganizationInfo aDOrganizationInfo = new ADOrganizationInfo();
        aDOrganizationInfo.setId(id);
        return aDOrganizationInfo;
    }
}
