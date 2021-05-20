package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingInvitationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingInvitation} and its DTO {@link MBiddingInvitationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CAnnouncementMapper.class, MBiddingMapper.class, CVendorMapper.class})
public interface MBiddingInvitationMapper extends EntityMapper<MBiddingInvitationDTO, MBiddingInvitation> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "announcement.id", target = "announcementId")
    @Mapping(source = "announcement.description", target = "announcementDescription")
    @Mapping(source = "announcement.publishDate", target = "announcementPublishDate")
    @Mapping(source = "announcement.biddingSchedule.endDate", target = "announcementEndDate")
    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MBiddingInvitationDTO toDto(MBiddingInvitation mBiddingInvitation);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "announcementId", target = "announcement")
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "vendorId", target = "vendor")
    MBiddingInvitation toEntity(MBiddingInvitationDTO mBiddingInvitationDTO);

    default MBiddingInvitation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingInvitation mBiddingInvitation = new MBiddingInvitation();
        mBiddingInvitation.setId(id);
        return mBiddingInvitation;
    }
}
