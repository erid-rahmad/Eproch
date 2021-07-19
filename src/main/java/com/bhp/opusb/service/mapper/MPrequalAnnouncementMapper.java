package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalAnnouncementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalAnnouncement} and its DTO {@link MPrequalAnnouncementDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MPrequalificationInformationMapper.class, MPrequalificationScheduleMapper.class, CAttachmentMapper.class})
public interface MPrequalAnnouncementMapper extends EntityMapper<MPrequalAnnouncementDTO, MPrequalAnnouncement> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "prequalification.name", target = "prequalificationName")
    @Mapping(source = "prequalification.documentNo", target = "preqDocumentNo")
    @Mapping(source = "prequalificationSchedule.id", target = "prequalificationScheduleId")
    @Mapping(source = "prequalificationSchedule.eventLine.prequalificationStep.name", target = "prequalificationScheduleName")
    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.fileName", target = "attachmentName")
    @Mapping(source = "attachment.downloadUrl", target = "attachmentUrl")
    MPrequalAnnouncementDTO toDto(MPrequalAnnouncement mPrequalAnnouncement);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "prequalificationId", target = "prequalification")
    @Mapping(source = "prequalificationScheduleId", target = "prequalificationSchedule")
    @Mapping(source = "attachmentId", target = "attachment")
    MPrequalAnnouncement toEntity(MPrequalAnnouncementDTO mPrequalAnnouncementDTO);

    default MPrequalAnnouncement fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalAnnouncement mPrequalAnnouncement = new MPrequalAnnouncement();
        mPrequalAnnouncement.setId(id);
        return mPrequalAnnouncement;
    }
}
