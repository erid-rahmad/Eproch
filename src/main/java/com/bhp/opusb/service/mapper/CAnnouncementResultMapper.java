package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CAnnouncementResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CAnnouncementResult} and its DTO {@link CAnnouncementResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingMapper.class, MBiddingScheduleMapper.class, CAttachmentMapper.class})
public interface CAnnouncementResultMapper extends EntityMapper<CAnnouncementResultDTO, CAnnouncementResult> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "biddingSchedule.id", target = "biddingScheduleId")
    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.fileName", target = "attachmentName")
    CAnnouncementResultDTO toDto(CAnnouncementResult cAnnouncementResult);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "biddingScheduleId", target = "biddingSchedule")
    @Mapping(source = "attachmentId", target = "attachment")
    CAnnouncementResult toEntity(CAnnouncementResultDTO cAnnouncementResultDTO);

    default CAnnouncementResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        CAnnouncementResult cAnnouncementResult = new CAnnouncementResult();
        cAnnouncementResult.setId(id);
        return cAnnouncementResult;
    }
}
