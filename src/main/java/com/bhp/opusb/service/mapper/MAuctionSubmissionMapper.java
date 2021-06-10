package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuctionSubmission;
import com.bhp.opusb.service.dto.MAuctionSubmissionDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuctionSubmission} and its DTO {@link MAuctionSubmissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MAuctionItemMapper.class})
public interface MAuctionSubmissionMapper extends EntityMapper<MAuctionSubmissionDTO, MAuctionSubmission> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "auctionItem.id", target = "auctionItemId")
    @Mapping(source = "auctionItem.product.name", target = "auctionItemName")
    @Mapping(source = "auctionItem.uom.code", target = "auctionItemUom")
    @Mapping(source = "auctionItem.quantity", target = "auctionItemQuantity")
    @Mapping(source = "auctionItem.ceilingPrice", target = "auctionItemCeilingPrice")
    @Mapping(source = "auctionItem.bidDecrement", target = "auctionItemBidDecrement")
    @Mapping(source = "auctionItem.protectBackBuffer", target = "auctionItemBackBuffer")
    @Mapping(source = "auctionItem.protectFrontBuffer", target = "auctionItemFrontBuffer")
    MAuctionSubmissionDTO toDto(MAuctionSubmission mAuctionSubmission);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "auctionItemId", target = "auctionItem")
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
