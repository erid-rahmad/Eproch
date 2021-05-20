package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MPrequalificationDateSet;
import com.bhp.opusb.service.dto.MPrequalificationDateSetDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MPrequalificationDateSet} and its DTO {@link MPrequalificationDateSetDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingScheduleMapper.class, ADOrganizationMapper.class})
public interface MPrequalificationDateSetMapper extends EntityMapper<MPrequalificationDateSetDTO, MPrequalificationDateSet> {

    @Mapping(source = "biddingSchedule.id", target = "biddingScheduleId")
    @Mapping(source = "biddingSchedule.eventTypeLine.sequence", target = "sequence")
    @Mapping(source = "biddingSchedule.eventTypeLine.CEvent.name", target = "biddingScheduleName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    MPrequalificationDateSetDTO toDto(MPrequalificationDateSet mPrequalificationDateSet);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(target = "biddingSchedule", ignore = true)
    MPrequalificationDateSet toEntity(MPrequalificationDateSetDTO mPrequalificationDateSetDTO);

    default MPrequalificationDateSet fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalificationDateSet mPrequalificationDateSet = new MPrequalificationDateSet();
        mPrequalificationDateSet.setId(id);
        return mPrequalificationDateSet;
    }
}
