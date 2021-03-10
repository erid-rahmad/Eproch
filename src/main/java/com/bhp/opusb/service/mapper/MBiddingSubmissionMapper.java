package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingSubmission} and its DTO {@link MBiddingSubmissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, MBiddingScheduleMapper.class, CVendorMapper.class, ADOrganizationMapper.class})
public interface MBiddingSubmissionMapper extends EntityMapper<MBiddingSubmissionDTO, MBiddingSubmission> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "biddingSchedule.id", target = "biddingScheduleId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    MBiddingSubmissionDTO toDto(MBiddingSubmission mBiddingSubmission);

    @Mapping(target = "mBiddingSubmissionLines", ignore = true)
    @Mapping(target = "removeMBiddingSubmissionLine", ignore = true)
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
