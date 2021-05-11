package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MBiddingSubmission} and its DTO {@link MBiddingSubmissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, MBiddingScheduleMapper.class, CVendorMapper.class, ADOrganizationMapper.class})
public interface MBiddingSubmissionMapper extends EntityMapper<MBiddingSubmissionDTO, MBiddingSubmission> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "bidding.documentNo", target = "biddingNo")
    @Mapping(source = "bidding.biddingType.name", target = "biddingTypeName")
    @Mapping(source = "bidding.biddingStatus", target = "biddingStatus")
    @Mapping(source = "biddingSchedule.id", target = "biddingScheduleId")
    @Mapping(source = "biddingSchedule.eventTypeLine.CEvent.name", target = "biddingScheduleName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    MBiddingSubmissionDTO toDto(MBiddingSubmission mBiddingSubmission);

    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "biddingScheduleId", target = "biddingSchedule")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    MBiddingSubmission toEntity(MBiddingSubmissionDTO mBiddingSubmissionDTO);

    default MBiddingSubmission fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingSubmission mBiddingSubmission = new MBiddingSubmission();
        mBiddingSubmission.setId(id);
        return mBiddingSubmission;
    }
}
