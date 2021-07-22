package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPrequalMethodCriteriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPrequalMethodCriteria} and its DTO {@link CPrequalMethodCriteriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {CBiddingCriteriaMapper.class, ADOrganizationMapper.class, CPrequalMethodLineMapper.class})
public interface CPrequalMethodCriteriaMapper extends EntityMapper<CPrequalMethodCriteriaDTO, CPrequalMethodCriteria> {

    @Mapping(source = "biddingCriteria.id", target = "biddingCriteriaId")
    @Mapping(source = "biddingCriteria.name", target = "biddingCriteriaName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "prequalMethodLine.id", target = "prequalMethodLineId")
    CPrequalMethodCriteriaDTO toDto(CPrequalMethodCriteria cPrequalMethodCriteria);

    @Mapping(source = "biddingCriteriaId", target = "biddingCriteria")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "prequalMethodLineId", target = "prequalMethodLine")
    CPrequalMethodCriteria toEntity(CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO);

    default CPrequalMethodCriteria fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPrequalMethodCriteria cPrequalMethodCriteria = new CPrequalMethodCriteria();
        cPrequalMethodCriteria.setId(id);
        return cPrequalMethodCriteria;
    }
}
