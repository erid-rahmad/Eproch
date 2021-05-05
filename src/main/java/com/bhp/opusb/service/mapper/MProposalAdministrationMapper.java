package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MProposalAdministration;
import com.bhp.opusb.service.dto.MProposalAdministrationDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MProposalAdministration} and its DTO {@link MProposalAdministrationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingSubmissionMapper.class})
public interface MProposalAdministrationMapper extends EntityMapper<MProposalAdministrationDTO, MProposalAdministration> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingSubmission.id", target = "biddingSubmissionId")
    @Mapping(source = "biddingSubmission.bidding.name", target = "biddingSubmissionName")
    MProposalAdministrationDTO toDto(MProposalAdministration mProposalAdministration);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingSubmissionId", target = "biddingSubmission")
    MProposalAdministration toEntity(MProposalAdministrationDTO mProposalAdministrationDTO);

    default MProposalAdministration fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProposalAdministration mProposalAdministration = new MProposalAdministration();
        mProposalAdministration.setId(id);
        return mProposalAdministration;
    }
}
