package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingSchedule} and its DTO {@link MBiddingScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, ADOrganizationMapper.class, CEventTypelineMapper.class})
public interface MBiddingScheduleMapper extends EntityMapper<MBiddingScheduleDTO, MBiddingSchedule> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "eventTypeLine.id", target = "eventTypeLineId")
    MBiddingScheduleDTO toDto(MBiddingSchedule mBiddingSchedule);

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
