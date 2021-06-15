package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuctionSubmission;
import com.bhp.opusb.service.dto.MAuctionSubmissionDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuctionSubmission} and its DTO {@link MAuctionSubmissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MAuctionMapper.class, CVendorMapper.class})
public interface MAuctionSubmissionMapper extends EntityMapper<MAuctionSubmissionDTO, MAuctionSubmission> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "auction.id", target = "auctionId")
    @Mapping(source = "auction.name", target = "auctionName")
    @Mapping(source = "auction.documentNo", target = "auctionDocumentNo")
    @Mapping(source = "auction.documentStatus", target = "auctionDocumentStatus")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MAuctionSubmissionDTO toDto(MAuctionSubmission mAuctionSubmission);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "auctionId", target = "auction")
    @Mapping(source = "vendorId", target = "vendor")
    MAuctionSubmission toEntity(MAuctionSubmissionDTO mAuctionSubmissionDTO);

    default MAuctionSubmission fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuctionSubmission mAuctionSubmission = new MAuctionSubmission();
        mAuctionSubmission.setId(id);
        return mAuctionSubmission;
    }
}
