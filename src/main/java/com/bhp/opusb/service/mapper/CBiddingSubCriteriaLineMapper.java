package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CBiddingSubCriteriaLine} and its DTO {@link CBiddingSubCriteriaLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CBiddingSubCriteriaMapper.class})
public interface CBiddingSubCriteriaLineMapper extends EntityMapper<CBiddingSubCriteriaLineDTO, CBiddingSubCriteriaLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "biddingSubCriteria.id", target = "biddingSubCriteriaId")
    CBiddingSubCriteriaLineDTO toDto(CBiddingSubCriteriaLine cBiddingSubCriteriaLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingSubCriteriaId", target = "biddingSubCriteria")
    CBiddingSubCriteriaLine toEntity(CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO);

    default CBiddingSubCriteriaLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine = new CBiddingSubCriteriaLine();
        cBiddingSubCriteriaLine.setId(id);
        return cBiddingSubCriteriaLine;
    }
}
