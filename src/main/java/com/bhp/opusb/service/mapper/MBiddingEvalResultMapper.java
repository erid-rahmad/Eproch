package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingEvalResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingEvalResult} and its DTO {@link MBiddingEvalResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingSubmissionMapper.class})
public interface MBiddingEvalResultMapper extends EntityMapper<MBiddingEvalResultDTO, MBiddingEvalResult> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "biddingSubmission.id", target = "biddingSubmissionId")
    @Mapping(source = "biddingSubmission.vendor.name", target = "vendorName")
    @Mapping(source = "biddingSubmission.vendor.id", target = "vendorId")
    @Mapping(source = "biddingSubmission.bidding.id", target = "biddingId")
    @Mapping(source = "biddingSubmission.bidding.name", target = "biddingName")
    @Mapping(source = "biddingSubmission.bidding.documentNo", target = "biddingNo")
    @Mapping(source = "biddingSubmission.bidding.biddingType.name", target = "biddingType")
    @Mapping(source = "biddingSubmission.dateSubmit", target = "submitDate")
    MBiddingEvalResultDTO toDto(MBiddingEvalResult mBiddingEvalResult);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingSubmissionId", target = "biddingSubmission")
    MBiddingEvalResult toEntity(MBiddingEvalResultDTO mBiddingEvalResultDTO);

    default MBiddingEvalResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingEvalResult mBiddingEvalResult = new MBiddingEvalResult();
        mBiddingEvalResult.setId(id);
        return mBiddingEvalResult;
    }
}
