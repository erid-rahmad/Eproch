package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MProposalTechnicalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MProposalTechnical} and its DTO {@link MProposalTechnicalDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingSubmissionMapper.class, CBiddingSubCriteriaLineMapper.class})
public interface MProposalTechnicalMapper extends EntityMapper<MProposalTechnicalDTO, MProposalTechnical> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingSubmission.id", target = "biddingSubmissionId")
    @Mapping(source = "biddingSubmission.bidding.name", target = "biddingSubmissionName")
    @Mapping(source = "biddingSubCriteriaLine.id", target = "biddingSubCriteriaLineId")
    @Mapping(source = "biddingSubCriteriaLine.name", target = "biddingSubCriteriaLineName")
    MProposalTechnicalDTO toDto(MProposalTechnical mProposalTechnical);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingSubmissionId", target = "biddingSubmission")
    @Mapping(source = "biddingSubCriteriaLineId", target = "biddingSubCriteriaLine")
    MProposalTechnical toEntity(MProposalTechnicalDTO mProposalTechnicalDTO);

    default MProposalTechnical fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProposalTechnical mProposalTechnical = new MProposalTechnical();
        mProposalTechnical.setId(id);
        return mProposalTechnical;
    }
}
