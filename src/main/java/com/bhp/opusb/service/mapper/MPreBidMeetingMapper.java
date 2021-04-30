package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MPreBidMeeting;
import com.bhp.opusb.service.dto.MPreBidMeetingDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MPreBidMeeting} and its DTO {@link MPreBidMeetingDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingScheduleMapper.class, ADOrganizationMapper.class})
public interface MPreBidMeetingMapper extends EntityMapper<MPreBidMeetingDTO, MPreBidMeeting> {

    @Mapping(source = "biddingSchedule.id", target = "biddingScheduleId")
    @Mapping(source = "biddingSchedule.eventTypeLine.CEvent.name", target = "biddingScheduleName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    MPreBidMeetingDTO toDto(MPreBidMeeting mPreBidMeeting);

    @Mapping(source = "biddingScheduleId", target = "biddingSchedule")
    @Mapping(target = "mPreBidMeetingAttachments", ignore = true)
    @Mapping(target = "removeMPreBidMeetingAttachment", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    MPreBidMeeting toEntity(MPreBidMeetingDTO mPreBidMeetingDTO);

    default MPreBidMeeting fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPreBidMeeting mPreBidMeeting = new MPreBidMeeting();
        mPreBidMeeting.setId(id);
        return mPreBidMeeting;
    }
}
