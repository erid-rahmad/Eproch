package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalificationSubmissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalificationSubmission} and its DTO {@link MPrequalificationSubmissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MPrequalificationInformationMapper.class, CDocumentTypeMapper.class, CVendorMapper.class})
public interface MPrequalificationSubmissionMapper extends EntityMapper<MPrequalificationSubmissionDTO, MPrequalificationSubmission> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "prequalification.name", target = "prequalificationName")
    @Mapping(source = "prequalification.documentNo", target = "prequalificationNo")
    @Mapping(source = "prequalification.type", target = "prequalificationType")
    @Mapping(source = "prequalification.preqEventName", target = "preqEventName")
    @Mapping(source = "prequalification.preqMethodId", target = "preqMethodId")
    @Mapping(source = "prequalification.status", target = "prequalificationStatus")
    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.name", target = "documentTypeName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MPrequalificationSubmissionDTO toDto(MPrequalificationSubmission mPrequalificationSubmission);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "prequalificationId", target = "prequalification")
    @Mapping(source = "documentTypeId", target = "documentType")
    @Mapping(source = "vendorId", target = "vendor")
    MPrequalificationSubmission toEntity(MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO);

    default MPrequalificationSubmission fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalificationSubmission mPrequalificationSubmission = new MPrequalificationSubmission();
        mPrequalificationSubmission.setId(id);
        return mPrequalificationSubmission;
    }
}
