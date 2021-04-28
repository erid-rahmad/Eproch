package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MBiddingSchedule} and its DTO {@link MBiddingScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, ADOrganizationMapper.class, CEventTypelineMapper.class})
public interface MBiddingScheduleMapper extends EntityMapper<MBiddingScheduleDTO, MBiddingSchedule> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.documentNo", target = "biddingNo")
    @Mapping(source = "bidding.name", target = "biddingTitle")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "eventTypeLine.id", target = "eventTypeLineId")
    @Mapping(source = "eventTypeLine.CEvent.name", target = "eventTypeLineName")
    @Mapping(source = "eventTypeLine.CEvent.adForm.id", target = "adFormId")
    MBiddingScheduleDTO toDto(MBiddingSchedule mBiddingSchedule);

    @Mapping(target = "mBiddingScheduleAttachments", ignore = true)
    @Mapping(target = "removeMBiddingScheduleAttachment", ignore = true)
    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "eventTypeLineId", target = "eventTypeLine")
    MBiddingSchedule toEntity(MBiddingScheduleDTO mBiddingScheduleDTO);

    default MBiddingSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingSchedule mBiddingSchedule = new MBiddingSchedule();
        mBiddingSchedule.setId(id);
        return mBiddingSchedule;
    }
}
