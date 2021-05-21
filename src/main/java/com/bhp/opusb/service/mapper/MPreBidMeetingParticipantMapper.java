package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MPreBidMeetingParticipant;
import com.bhp.opusb.service.dto.MPreBidMeetingParticipantDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MPreBidMeetingParticipant} and its DTO {@link MPreBidMeetingParticipantDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MPreBidMeetingMapper.class, CVendorMapper.class})
public interface MPreBidMeetingParticipantMapper extends EntityMapper<MPreBidMeetingParticipantDTO, MPreBidMeetingParticipant> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "preBidMeeting.id", target = "preBidMeetingId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MPreBidMeetingParticipantDTO toDto(MPreBidMeetingParticipant mPreBidMeetingParticipant);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "preBidMeetingId", target = "preBidMeeting")
    @Mapping(source = "vendorId", target = "vendor")
    MPreBidMeetingParticipant toEntity(MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO);

    default MPreBidMeetingParticipant fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPreBidMeetingParticipant mPreBidMeetingParticipant = new MPreBidMeetingParticipant();
        mPreBidMeetingParticipant.setId(id);
        return mPreBidMeetingParticipant;
    }
}
