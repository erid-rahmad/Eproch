package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MBiddingScheduleAttachment;
import com.bhp.opusb.service.dto.MBiddingScheduleAttachmentDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MBiddingScheduleAttachment} and its DTO {@link MBiddingScheduleAttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, ADOrganizationMapper.class, MBiddingScheduleMapper.class})
public interface MBiddingScheduleAttachmentMapper extends EntityMapper<MBiddingScheduleAttachmentDTO, MBiddingScheduleAttachment> {

    @Mapping(source = "CAttachment.id", target = "CAttachmentId")
    @Mapping(source = "CAttachment.fileName", target = "CAttachmentName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingSchedule.id", target = "biddingScheduleId")
//    @Mapping(source = "biddingSchedule.eventTypeLine.event", target = "biddingScheduleName")
    MBiddingScheduleAttachmentDTO toDto(MBiddingScheduleAttachment mBiddingScheduleAttachment);

    @Mapping(source = "CAttachmentId", target = "CAttachment")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingScheduleId", target = "biddingSchedule")
    MBiddingScheduleAttachment toEntity(MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO);

    default MBiddingScheduleAttachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingScheduleAttachment mBiddingScheduleAttachment = new MBiddingScheduleAttachment();
        mBiddingScheduleAttachment.setId(id);
        return mBiddingScheduleAttachment;
    }
}
