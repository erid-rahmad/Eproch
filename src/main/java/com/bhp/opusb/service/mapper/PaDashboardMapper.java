package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.PaDashboardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaDashboard} and its DTO {@link PaDashboardDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface PaDashboardMapper extends EntityMapper<PaDashboardDTO, PaDashboard> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    PaDashboardDTO toDto(PaDashboard paDashboard);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    PaDashboard toEntity(PaDashboardDTO paDashboardDTO);

    default PaDashboard fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaDashboard paDashboard = new PaDashboard();
        paDashboard.setId(id);
        return paDashboard;
    }
}
