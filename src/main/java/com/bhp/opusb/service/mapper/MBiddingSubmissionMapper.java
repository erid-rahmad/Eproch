package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MBiddingSubmission} and its DTO {@link MBiddingSubmissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingMapper.class, MBiddingScheduleMapper.class, CDocumentTypeMapper.class, CVendorMapper.class})
public interface MBiddingSubmissionMapper extends EntityMapper<MBiddingSubmissionDTO, MBiddingSubmission> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "bidding.documentNo", target = "biddingNo")
    @Mapping(source = "bidding.biddingType.name", target = "biddingTypeName")
    @Mapping(source = "bidding.biddingStatus", target = "biddingStatus")
    @Mapping(source = "bidding.ceilingPrice", target = "ceilingPrice")
    @Mapping(source = "bidding.currency.code", target = "currencyName")
    @Mapping(source = "bidding.eventType.name", target = "eventTypeName")
    @Mapping(source = "bidding.vendorSelection", target = "vendorSelection")
    @Mapping(source = "bidding.adUser.user.firstName", target = "biddingUser")
    @Mapping(source = "biddingSchedule.id", target = "biddingScheduleId")
    @Mapping(source = "biddingSchedule.eventTypeLine.CEvent.name", target = "biddingScheduleName")
    @Mapping(source = "biddingSchedule.dateSet.status", target = "biddingScheduleStatus")
    @Mapping(source = "biddingSchedule.dateSet.endDate", target = "biddingScheduleEndDate")
    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.name", target = "documentTypeName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MBiddingSubmissionDTO toDto(MBiddingSubmission mBiddingSubmission);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "biddingScheduleId", target = "biddingSchedule")
    @Mapping(source = "documentTypeId", target = "documentType")
    @Mapping(source = "vendorId", target = "vendor")
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
