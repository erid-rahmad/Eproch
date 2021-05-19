package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingResult} and its DTO {@link MBiddingResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CAnnouncementResultMapper.class, MBiddingMapper.class, CVendorMapper.class, MBiddingEvalResultMapper.class})
public interface MBiddingResultMapper extends EntityMapper<MBiddingResultDTO, MBiddingResult> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "announcementResult.id", target = "announcementResultId")
    @Mapping(source = "announcementResult.description", target = "announcementResultName")
    @Mapping(source = "announcementResult.attachment.fileName", target = "attachmentName")
    @Mapping(source = "announcementResult.attachment.uploadDir", target = "uploadDir")
    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "bidding.biddingStatus", target = "biddingStatus")
    @Mapping(source = "bidding.documentNo", target = "biddingNo")
    @Mapping(source = "bidding.documentType.name", target = "biddingType")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "biddingEvalResult.id", target = "biddingEvalResultId")
    MBiddingResultDTO toDto(MBiddingResult mBiddingResult);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "announcementResultId", target = "announcementResult")
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "biddingEvalResultId", target = "biddingEvalResult")
    MBiddingResult toEntity(MBiddingResultDTO mBiddingResultDTO);

    default MBiddingResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingResult mBiddingResult = new MBiddingResult();
        mBiddingResult.setId(id);
        return mBiddingResult;
    }
}
