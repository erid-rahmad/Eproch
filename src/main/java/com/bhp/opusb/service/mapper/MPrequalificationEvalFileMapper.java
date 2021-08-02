package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalificationEvalFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalificationEvalFile} and its DTO {@link MPrequalificationEvalFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, ADOrganizationMapper.class, MPrequalificationSubmissionMapper.class, CBiddingSubCriteriaMapper.class})
public interface MPrequalificationEvalFileMapper extends EntityMapper<MPrequalificationEvalFileDTO, MPrequalificationEvalFile> {

    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.fileName", target = "attachmentName")
    @Mapping(source = "attachment.downloadUrl", target = "attachmentUrl")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "prequalificationSubmission.id", target = "prequalificationSubmissionId")
    @Mapping(source = "biddingSubCriteria.id", target = "biddingSubCriteriaId")
    MPrequalificationEvalFileDTO toDto(MPrequalificationEvalFile mPrequalificationEvalFile);

    @Mapping(source = "attachmentId", target = "attachment")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "prequalificationSubmissionId", target = "prequalificationSubmission")
    @Mapping(source = "biddingSubCriteriaId", target = "biddingSubCriteria")
    MPrequalificationEvalFile toEntity(MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO);

    default MPrequalificationEvalFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalificationEvalFile mPrequalificationEvalFile = new MPrequalificationEvalFile();
        mPrequalificationEvalFile.setId(id);
        return mPrequalificationEvalFile;
    }
}
