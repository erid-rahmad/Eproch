package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuctionSubmissionLog;
import com.bhp.opusb.service.dto.MAuctionSubmissionLogDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuctionSubmissionLog} and its DTO {@link MAuctionSubmissionLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {MAuctionItemMapper.class})
public interface MAuctionSubmissionLogMapper extends EntityMapper<MAuctionSubmissionLogDTO, MAuctionSubmissionLog> {

    @Mapping(source = "auctionItem.id", target = "auctionItemId")
    MAuctionSubmissionLogDTO toDto(MAuctionSubmissionLog mAuctionSubmissionLog);

    @Mapping(source = "auctionItemId", target = "auctionItem")
    MAuctionSubmissionLog toEntity(MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO);

    default MAuctionSubmissionLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuctionSubmissionLog mAuctionSubmissionLog = new MAuctionSubmissionLog();
        mAuctionSubmissionLog.setId(id);
        return mAuctionSubmissionLog;
    }
}
