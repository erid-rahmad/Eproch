package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuctionContent;
import com.bhp.opusb.service.dto.MAuctionContentDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuctionContent} and its DTO {@link MAuctionContentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MAuctionMapper.class})
public interface MAuctionContentMapper extends EntityMapper<MAuctionContentDTO, MAuctionContent> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "auction.id", target = "auctionId")
    @Mapping(source = "auction.name", target = "auctionName")
    MAuctionContentDTO toDto(MAuctionContent mAuctionContent);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "auctionId", target = "auction")
    MAuctionContent toEntity(MAuctionContentDTO mAuctionContentDTO);

    default MAuctionContent fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuctionContent mAuctionContent = new MAuctionContent();
        mAuctionContent.setId(id);
        return mAuctionContent;
    }
}