package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MPreBidMeetingAttachment;
import com.bhp.opusb.service.dto.MPreBidMeetingAttachmentDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MPreBidMeetingAttachment} and its DTO {@link MPreBidMeetingAttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, ADOrganizationMapper.class, MPreBidMeetingMapper.class})
public interface MPreBidMeetingAttachmentMapper extends EntityMapper<MPreBidMeetingAttachmentDTO, MPreBidMeetingAttachment> {

    @Mapping(source = "CAttachment.id", target = "CAttachmentId")
    @Mapping(source = "CAttachment.fileName", target = "CAttachmentName")
    @Mapping(source = "CAttachment.downloadUrl", target = "CAttachmentUrl")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "preBidMeeting.id", target = "preBidMeetingId")
    @Mapping(source = "preBidMeeting.biddingSchedule.eventTypeLine.CEvent.name", target = "preBidMeetingName")
    MPreBidMeetingAttachmentDTO toDto(MPreBidMeetingAttachment mPreBidMeetingAttachment);

    @Mapping(source = "CAttachmentId", target = "CAttachment")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "preBidMeetingId", target = "preBidMeeting")
    MPreBidMeetingAttachment toEntity(MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO);

    default MPreBidMeetingAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPreBidMeetingAttachment mPreBidMeetingAttachment = new MPreBidMeetingAttachment();
        mPreBidMeetingAttachment.setId(id);
        return mPreBidMeetingAttachment;
    }
}
