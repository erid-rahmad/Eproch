package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CProductClassificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CProductClassification} and its DTO {@link CProductClassificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CProductClassificationMapper extends EntityMapper<CProductClassificationDTO, CProductClassification> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CProductClassificationDTO toDto(CProductClassification cProductClassification);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CProductClassification toEntity(CProductClassificationDTO cProductClassificationDTO);

    default CProductClassification fromId(Long id) {
        if (id == null) {
            return null;
        }
        CProductClassification cProductClassification = new CProductClassification();
        cProductClassification.setId(id);
        return cProductClassification;
    }
}
