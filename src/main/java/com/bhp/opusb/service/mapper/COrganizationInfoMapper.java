package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.COrganizationInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link COrganizationInfo} and its DTO {@link COrganizationInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, COrganizationMapper.class})
public interface COrganizationInfoMapper extends EntityMapper<COrganizationInfoDTO, COrganizationInfo> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "parentOrganization.id", target = "parentOrganizationId")
    @Mapping(source = "parentOrganization.name", target = "parentOrganizationName")
    COrganizationInfoDTO toDto(COrganizationInfo cOrganizationInfo);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "parentOrganizationId", target = "parentOrganization")
    COrganizationInfo toEntity(COrganizationInfoDTO cOrganizationInfoDTO);

    default COrganizationInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        COrganizationInfo cOrganizationInfo = new COrganizationInfo();
        cOrganizationInfo.setId(id);
        return cOrganizationInfo;
    }
}
