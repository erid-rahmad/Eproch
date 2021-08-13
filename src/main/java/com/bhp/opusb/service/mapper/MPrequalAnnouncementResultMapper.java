package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalAnnouncementResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalAnnouncementResult} and its DTO {@link MPrequalAnnouncementResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MPrequalificationInformationMapper.class, MPrequalificationScheduleMapper.class, CAttachmentMapper.class})
public interface MPrequalAnnouncementResultMapper extends EntityMapper<MPrequalAnnouncementResultDTO, MPrequalAnnouncementResult> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "prequalificationSchedule.id", target = "prequalificationScheduleId")
    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.fileName", target = "attachmentName")
    MPrequalAnnouncementResultDTO toDto(MPrequalAnnouncementResult mPrequalAnnouncementResult);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "prequalificationId", target = "prequalification")
    @Mapping(source = "prequalificationScheduleId", target = "prequalificationSchedule")
    @Mapping(source = "attachmentId", target = "attachment")
    MPrequalAnnouncementResult toEntity(MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO);

    default MPrequalAnnouncementResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalAnnouncementResult mPrequalAnnouncementResult = new MPrequalAnnouncementResult();
        mPrequalAnnouncementResult.setId(id);
        return mPrequalAnnouncementResult;
    }
}
