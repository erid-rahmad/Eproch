package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuctionSubmissionItem;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuctionSubmissionItem} and its DTO {@link MAuctionSubmissionItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MAuctionSubmissionMapper.class, MAuctionItemMapper.class})
public interface MAuctionSubmissionItemMapper extends EntityMapper<MAuctionSubmissionItemDTO, MAuctionSubmissionItem> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "auctionSubmission.id", target = "auctionSubmissionId")
    @Mapping(source = "auctionItem.id", target = "auctionItemId")
    MAuctionSubmissionItemDTO toDto(MAuctionSubmissionItem mAuctionSubmissionItem);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "auctionSubmissionId", target = "auctionSubmission")
    @Mapping(source = "auctionItemId", target = "auctionItem")
    MAuctionSubmissionItem toEntity(MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO);

    default MAuctionSubmissionItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuctionSubmissionItem mAuctionSubmissionItem = new MAuctionSubmissionItem();
        mAuctionSubmissionItem.setId(id);
        return mAuctionSubmissionItem;
    }
}
