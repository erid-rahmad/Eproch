package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.PaDashboardPreference;
import com.bhp.opusb.service.dto.PaDashboardPreferenceDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link PaDashboardPreference} and its DTO {@link PaDashboardPreferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdUserMapper.class, PaDashboardItemMapper.class})
public interface PaDashboardPreferenceMapper extends EntityMapper<PaDashboardPreferenceDTO, PaDashboardPreference> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adUser.user.id", target = "adUserUserId")
    @Mapping(source = "adUser.user.login", target = "adUserUserName")
    @Mapping(source = "paDashboardItem.id", target = "paDashboardItemId")
    @Mapping(source = "paDashboardItem.name", target = "paDashboardItemName")
    PaDashboardPreferenceDTO toDto(PaDashboardPreference paDashboardPreference);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adUserUserId", target = "adUser")
    @Mapping(source = "paDashboardItemId", target = "paDashboardItem")
    PaDashboardPreference toEntity(PaDashboardPreferenceDTO paDashboardPreferenceDTO);

    default PaDashboardPreference fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaDashboardPreference paDashboardPreference = new PaDashboardPreference();
        paDashboardPreference.setId(id);
        return paDashboardPreference;
    }
}
