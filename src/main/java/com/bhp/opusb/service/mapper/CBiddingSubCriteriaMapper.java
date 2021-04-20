package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CBiddingSubCriteria} and its DTO {@link CBiddingSubCriteriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CBiddingCriteriaMapper.class})
public interface CBiddingSubCriteriaMapper extends EntityMapper<CBiddingSubCriteriaDTO, CBiddingSubCriteria> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingCriteria.id", target = "biddingCriteriaId")
    @Mapping(source = "biddingCriteria.name", target = "biddingCriteriaName")

    CBiddingSubCriteriaDTO toDto(CBiddingSubCriteria cBiddingSubCriteria);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingCriteriaId", target = "biddingCriteria")
    CBiddingSubCriteria toEntity(CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO);

    default CBiddingSubCriteria fromId(Long id) {
        if (id == null) {
            return null;
        }
        CBiddingSubCriteria cBiddingSubCriteria = new CBiddingSubCriteria();
        cBiddingSubCriteria.setId(id);
        return cBiddingSubCriteria;
    }
}
