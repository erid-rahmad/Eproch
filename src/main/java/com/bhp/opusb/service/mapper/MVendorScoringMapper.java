package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVendorScoringDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVendorScoring} and its DTO {@link MVendorScoringDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, ADOrganizationMapper.class, CBiddingCriteriaMapper.class, CBiddingSubCriteriaMapper.class, AdUserMapper.class})
public interface MVendorScoringMapper extends EntityMapper<MVendorScoringDTO, MVendorScoring> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingCriteria.id", target = "biddingCriteriaId")
    @Mapping(source = "biddingCriteria.name", target = "biddingCriteriaName")
    @Mapping(source = "biddingSubCriteria.id", target = "biddingSubCriteriaId")
    @Mapping(source = "biddingSubCriteria.name", target = "biddingSubCriteriaName")
    @Mapping(source = "adUser.user.id", target = "adUserUserId")
    @Mapping(source = "adUser.user.login", target = "adUserUserName")
    MVendorScoringDTO toDto(MVendorScoring mVendorScoring);

    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingCriteriaId", target = "biddingCriteria")
    @Mapping(source = "biddingSubCriteriaId", target = "biddingSubCriteria")
    @Mapping(source = "adUserUserId", target = "adUser")
    MVendorScoring toEntity(MVendorScoringDTO mVendorScoringDTO);

    default MVendorScoring fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorScoring mVendorScoring = new MVendorScoring();
        mVendorScoring.setId(id);
        return mVendorScoring;
    }
}
