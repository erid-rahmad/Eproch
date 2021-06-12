package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuctionInvitation;
import com.bhp.opusb.service.dto.MAuctionInvitationDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuctionInvitation} and its DTO {@link MAuctionInvitationDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MAuctionMapper.class, CDocumentTypeMapper.class, CVendorMapper.class})
public interface MAuctionInvitationMapper extends EntityMapper<MAuctionInvitationDTO, MAuctionInvitation> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "auction.id", target = "auctionId")
    @Mapping(source = "auction.documentNo", target = "auctionDocumentNo")
    @Mapping(source = "auction.documentStatus", target = "auctionDocumentStatus")
    @Mapping(source = "auction.name", target = "auctionName")
    @Mapping(source = "auction.currency.id", target = "auctionCurrencyId")
    @Mapping(source = "auction.content.id", target = "auctionContentId")
    @Mapping(source = "auction.prerequisite.id", target = "auctionPrerequisiteId")
    @Mapping(source = "auction.rule.id", target = "auctionRuleId")
    @Mapping(source = "auction.rule.startDate", target = "auctionRuleStartDate")
    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.name", target = "documentTypeName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MAuctionInvitationDTO toDto(MAuctionInvitation mAuctionInvitation);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "auctionId", target = "auction")
    @Mapping(source = "documentTypeId", target = "documentType")
    @Mapping(source = "vendorId", target = "vendor")
    MAuctionInvitation toEntity(MAuctionInvitationDTO mAuctionInvitationDTO);

    default MAuctionInvitation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuctionInvitation mAuctionInvitation = new MAuctionInvitation();
        mAuctionInvitation.setId(id);
        return mAuctionInvitation;
    }
}
