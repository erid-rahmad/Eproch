package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MVendorScoringDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MVendorScoring} and its DTO {@link MVendorScoringDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, ADOrganizationMapper.class, CEvaluationMethodMapper.class})
public interface MVendorScoringMapper extends EntityMapper<MVendorScoringDTO, MVendorScoring> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "evaluationMethod.id", target = "evaluationMethodId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "evaluationMethod.name", target = "evaluationMethodName")
    MVendorScoringDTO toDto(MVendorScoring mVendorScoring);

    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "evaluationMethodId", target = "evaluationMethod")
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
