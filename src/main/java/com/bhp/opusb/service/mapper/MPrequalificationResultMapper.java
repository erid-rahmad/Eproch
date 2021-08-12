package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalificationResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalificationResult} and its DTO {@link MPrequalificationResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MPrequalAnnouncementResultMapper.class, MPrequalificationInformationMapper.class, CVendorMapper.class, MPrequalificationSubmissionMapper.class})
public interface MPrequalificationResultMapper extends EntityMapper<MPrequalificationResultDTO, MPrequalificationResult> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "announcementResult.id", target = "announcementResultId")
    @Mapping(source = "announcementResult.attachment.fileName", target = "attachmentName")
    @Mapping(source = "announcementResult.attachment.id", target = "attachmentId")
    @Mapping(source = "announcementResult.attachment.downloadUrl", target = "attachmentUrl")
    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "prequalification.name", target = "prequalificationName")
    @Mapping(source = "prequalification.status", target = "prequalificationStatus")
    @Mapping(source = "prequalification.documentNo", target = "prequalificationNo")
    @Mapping(source = "prequalification.type", target = "prequalificationType")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "submission.id", target = "submissionId")
    MPrequalificationResultDTO toDto(MPrequalificationResult mPrequalificationResult);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "announcementResultId", target = "announcementResult")
    @Mapping(source = "prequalificationId", target = "prequalification")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "submissionId", target = "submission")
    MPrequalificationResult toEntity(MPrequalificationResultDTO mPrequalificationResultDTO);

    default MPrequalificationResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalificationResult mPrequalificationResult = new MPrequalificationResult();
        mPrequalificationResult.setId(id);
        return mPrequalificationResult;
    }
}
