package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MProposalTechnicalFile;
import com.bhp.opusb.service.dto.MProposalTechnicalFileDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MProposalTechnicalFile} and its DTO {@link MProposalTechnicalFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, ADOrganizationMapper.class, MBiddingSubmissionMapper.class, CBiddingSubCriteriaMapper.class})
public interface MProposalTechnicalFileMapper extends EntityMapper<MProposalTechnicalFileDTO, MProposalTechnicalFile> {

    @Mapping(source = "CAttachment.id", target = "CAttachmentId")
    @Mapping(source = "CAttachment.fileName", target = "CAttachmentName")
    @Mapping(source = "CAttachment.downloadUrl", target = "attachmentUrl")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingSubmission.id", target = "biddingSubmissionId")
    @Mapping(source = "biddingSubmission.bidding.name", target = "biddingSubmissionName")
    @Mapping(source = "biddingSubCriteria.id", target = "biddingSubCriteriaId")
    @Mapping(source = "biddingSubCriteria.name", target = "biddingSubCriteriaName")
    MProposalTechnicalFileDTO toDto(MProposalTechnicalFile mProposalTechnicalFile);

    @Mapping(source = "CAttachmentId", target = "CAttachment")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingSubmissionId", target = "biddingSubmission")
    @Mapping(source = "biddingSubCriteriaId", target = "biddingSubCriteria")
    MProposalTechnicalFile toEntity(MProposalTechnicalFileDTO mProposalTechnicalFileDTO);

    default MProposalTechnicalFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProposalTechnicalFile mProposalTechnicalFile = new MProposalTechnicalFile();
        mProposalTechnicalFile.setId(id);
        return mProposalTechnicalFile;
    }
}
