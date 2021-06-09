package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuctionParticipant;
import com.bhp.opusb.service.dto.MAuctionParticipantDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuctionParticipant} and its DTO {@link MAuctionParticipantDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MAuctionMapper.class, AdUserMapper.class, CVendorMapper.class})
public interface MAuctionParticipantMapper extends EntityMapper<MAuctionParticipantDTO, MAuctionParticipant> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "auction.id", target = "auctionId")
    @Mapping(source = "auction.name", target = "auctionName")
    @Mapping(source = "auction.documentNo", target = "auctionDocumentNo")
    @Mapping(source = "auction.currency.id", target = "auctionCurrencyId")
    @Mapping(source = "auction.currency.code", target = "auctionCurrencyName")
    @Mapping(source = "auction.rule.startDate", target = "auctionStartDate")
    @Mapping(source = "user.user.id", target = "userUserId")
    @Mapping(source = "user.user.login", target = "userName")
    @Mapping(source = "user.user.email", target = "userEmail")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MAuctionParticipantDTO toDto(MAuctionParticipant mAuctionParticipant);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "auctionId", target = "auction")
    @Mapping(source = "userUserId", target = "user")
    @Mapping(source = "vendorId", target = "vendor")
    MAuctionParticipant toEntity(MAuctionParticipantDTO mAuctionParticipantDTO);

    default MAuctionParticipant fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuctionParticipant mAuctionParticipant = new MAuctionParticipant();
        mAuctionParticipant.setId(id);
        return mAuctionParticipant;
    }
}
