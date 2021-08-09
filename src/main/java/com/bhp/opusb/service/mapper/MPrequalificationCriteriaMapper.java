package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalificationCriteriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalificationCriteria} and its DTO {@link MPrequalificationCriteriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MPrequalificationInformationMapper.class, CPrequalMethodCriteriaMapper.class, CPrequalMethodSubCriteriaMapper.class, CBiddingSubCriteriaLineMapper.class})
public interface MPrequalificationCriteriaMapper extends EntityMapper<MPrequalificationCriteriaDTO, MPrequalificationCriteria> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "prequalMethodCriteria.id", target = "prequalMethodCriteriaId")
    @Mapping(source = "prequalMethodSubCriteria.id", target = "prequalMethodSubCriteriaId")
    @Mapping(source = "biddingSubCriteriaLine.id", target = "biddingSubCriteriaLineId")
    MPrequalificationCriteriaDTO toDto(MPrequalificationCriteria mPrequalificationCriteria);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "prequalificationId", target = "prequalification")
    @Mapping(source = "prequalMethodCriteriaId", target = "prequalMethodCriteria")
    @Mapping(source = "prequalMethodSubCriteriaId", target = "prequalMethodSubCriteria")
    @Mapping(source = "biddingSubCriteriaLineId", target = "biddingSubCriteriaLine")
    MPrequalificationCriteria toEntity(MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO);

    default MPrequalificationCriteria fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalificationCriteria mPrequalificationCriteria = new MPrequalificationCriteria();
        mPrequalificationCriteria.setId(id);
        return mPrequalificationCriteria;
    }
}
