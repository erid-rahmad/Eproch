package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CEvalMethodCriteriaLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CEvalMethodCriteriaLine} and its DTO {@link CEvalMethodCriteriaLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CBiddingCriteriaMapper.class})
public interface CEvalMethodCriteriaLineMapper extends EntityMapper<CEvalMethodCriteriaLineDTO, CEvalMethodCriteriaLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "biddingCriteria.id", target = "biddingCriteriaId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingCriteria.name", target = "biddingCriteriaName")
    CEvalMethodCriteriaLineDTO toDto(CEvalMethodCriteriaLine cEvalMethodCriteriaLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingCriteriaId", target = "biddingCriteria")
    CEvalMethodCriteriaLine toEntity(CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO);

    default CEvalMethodCriteriaLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEvalMethodCriteriaLine cEvalMethodCriteriaLine = new CEvalMethodCriteriaLine();
        cEvalMethodCriteriaLine.setId(id);
        return cEvalMethodCriteriaLine;
    }
}
