package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CBiddingTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CBiddingType} and its DTO {@link CBiddingTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CProductClassificationMapper.class})
public interface CBiddingTypeMapper extends EntityMapper<CBiddingTypeDTO, CBiddingType> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "productClassification.id", target = "productClassificationId")
    @Mapping(source = "productClassification.name", target = "productClassificationName")
    CBiddingTypeDTO toDto(CBiddingType cBiddingType);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "productClassificationId", target = "productClassification")
    CBiddingType toEntity(CBiddingTypeDTO cBiddingTypeDTO);

    default CBiddingType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CBiddingType cBiddingType = new CBiddingType();
        cBiddingType.setId(id);
        return cBiddingType;
    }
}
