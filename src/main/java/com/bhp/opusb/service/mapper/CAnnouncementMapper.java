package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CAnnouncementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CAnnouncement} and its DTO {@link CAnnouncementDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingMapper.class, MBiddingScheduleMapper.class, CAttachmentMapper.class})
public interface CAnnouncementMapper extends EntityMapper<CAnnouncementDTO, CAnnouncement> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "biddingSchedule.id", target = "biddingScheduleId")
    @Mapping(source = "attachment.id", target = "attachmentId")
    CAnnouncementDTO toDto(CAnnouncement cAnnouncement);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "biddingScheduleId", target = "biddingSchedule")
    @Mapping(source = "attachmentId", target = "attachment")
    CAnnouncement toEntity(CAnnouncementDTO cAnnouncementDTO);

    default CAnnouncement fromId(Long id) {
        if (id == null) {
            return null;
        }
        CAnnouncement cAnnouncement = new CAnnouncement();
        cAnnouncement.setId(id);
        return cAnnouncement;
    }
}
