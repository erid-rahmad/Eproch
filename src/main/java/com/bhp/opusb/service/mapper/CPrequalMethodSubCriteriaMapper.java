package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPrequalMethodSubCriteriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPrequalMethodSubCriteria} and its DTO {@link CPrequalMethodSubCriteriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CBiddingCriteriaMapper.class, CBiddingSubCriteriaMapper.class, CPrequalMethodCriteriaMapper.class})
public interface CPrequalMethodSubCriteriaMapper extends EntityMapper<CPrequalMethodSubCriteriaDTO, CPrequalMethodSubCriteria> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "biddingCriteria.id", target = "biddingCriteriaId")
    @Mapping(source = "biddingSubCriteria.id", target = "biddingSubCriteriaId")
    @Mapping(source = "biddingSubCriteria.name", target = "biddingSubCriteriaName")
    @Mapping(source = "prequalMethodCriteria.id", target = "prequalMethodCriteriaId")
    CPrequalMethodSubCriteriaDTO toDto(CPrequalMethodSubCriteria cPrequalMethodSubCriteria);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingCriteriaId", target = "biddingCriteria")
    @Mapping(source = "biddingSubCriteriaId", target = "biddingSubCriteria")
    @Mapping(source = "prequalMethodCriteriaId", target = "prequalMethodCriteria")
    CPrequalMethodSubCriteria toEntity(CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO);

    default CPrequalMethodSubCriteria fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPrequalMethodSubCriteria cPrequalMethodSubCriteria = new CPrequalMethodSubCriteria();
        cPrequalMethodSubCriteria.setId(id);
        return cPrequalMethodSubCriteria;
    }
}
