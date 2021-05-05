package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MProposalAdministrationFile;
import com.bhp.opusb.service.dto.MProposalAdministrationFileDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MProposalAdministrationFile} and its DTO {@link MProposalAdministrationFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, ADOrganizationMapper.class, MBiddingSubmissionMapper.class, CBiddingSubCriteriaMapper.class})
public interface MProposalAdministrationFileMapper extends EntityMapper<MProposalAdministrationFileDTO, MProposalAdministrationFile> {

    @Mapping(source = "CAttachment.id", target = "CAttachmentId")
    @Mapping(source = "CAttachment.fileName", target = "CAttachmentName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingSubmission.id", target = "biddingSubmissionId")
    @Mapping(source = "biddingSubmission.bidding.name", target = "biddingSubmissionName")
    @Mapping(source = "biddingSubCriteria.id", target = "biddingSubCriteriaId")
    @Mapping(source = "biddingSubCriteria.name", target = "biddingSubCriteriaName")
    MProposalAdministrationFileDTO toDto(MProposalAdministrationFile mProposalAdministrationFile);

    @Mapping(source = "CAttachmentId", target = "CAttachment")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingSubmissionId", target = "biddingSubmission")
    @Mapping(source = "biddingSubCriteriaId", target = "biddingSubCriteria")
    MProposalAdministrationFile toEntity(MProposalAdministrationFileDTO mProposalAdministrationFileDTO);

    default MProposalAdministrationFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProposalAdministrationFile mProposalAdministrationFile = new MProposalAdministrationFile();
        mProposalAdministrationFile.setId(id);
        return mProposalAdministrationFile;
    }
}
