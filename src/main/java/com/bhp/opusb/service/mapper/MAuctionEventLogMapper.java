package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuctionEventLog;
import com.bhp.opusb.service.dto.MAuctionEventLogDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuctionEventLog} and its DTO {@link MAuctionEventLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {MAuctionMapper.class, MAuctionItemMapper.class, CVendorMapper.class})
public interface MAuctionEventLogMapper extends EntityMapper<MAuctionEventLogDTO, MAuctionEventLog> {

    @Mapping(source = "auction.id", target = "auctionId")
    @Mapping(source = "auction.documentNo", target = "auctionName")
    @Mapping(source = "auctionItem.id", target = "auctionItemId")
    @Mapping(source = "auctionItem.product.name", target = "auctionItemName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MAuctionEventLogDTO toDto(MAuctionEventLog mAuctionEventLog);

    @Mapping(source = "auctionId", target = "auction")
    @Mapping(source = "auctionItemId", target = "auctionItem")
    @Mapping(source = "vendorId", target = "vendor")
    MAuctionEventLog toEntity(MAuctionEventLogDTO mAuctionEventLogDTO);

    default MAuctionEventLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuctionEventLog mAuctionEventLog = new MAuctionEventLog();
        mAuctionEventLog.setId(id);
        return mAuctionEventLog;
    }
}
