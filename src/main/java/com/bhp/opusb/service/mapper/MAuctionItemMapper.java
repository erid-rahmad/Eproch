package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuctionItem;
import com.bhp.opusb.service.dto.MAuctionItemDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuctionItem} and its DTO {@link MAuctionItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MAuctionMapper.class, CProductMapper.class, CUnitOfMeasureMapper.class})
public interface MAuctionItemMapper extends EntityMapper<MAuctionItemDTO, MAuctionItem> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "auction.id", target = "auctionId")
    @Mapping(source = "auction.name", target = "auctionName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.code", target = "productCode")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.code", target = "uomName")
    MAuctionItemDTO toDto(MAuctionItem mAuctionItem);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "auctionId", target = "auction")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "uomId", target = "uom")
    MAuctionItem toEntity(MAuctionItemDTO mAuctionItemDTO);

    default MAuctionItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuctionItem mAuctionItem = new MAuctionItem();
        mAuctionItem.setId(id);
        return mAuctionItem;
    }
}
