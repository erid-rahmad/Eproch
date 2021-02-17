package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CBiddingCriteriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CBiddingCriteria} and its DTO {@link CBiddingCriteriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CBiddingCriteriaMapper extends EntityMapper<CBiddingCriteriaDTO, CBiddingCriteria> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CBiddingCriteriaDTO toDto(CBiddingCriteria cBiddingCriteria);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CBiddingCriteria toEntity(CBiddingCriteriaDTO cBiddingCriteriaDTO);

    default CBiddingCriteria fromId(Long id) {
        if (id == null) {
            return null;
        }
        CBiddingCriteria cBiddingCriteria = new CBiddingCriteria();
        cBiddingCriteria.setId(id);
        return cBiddingCriteria;
    }
}
