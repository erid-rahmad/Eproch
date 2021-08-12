package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalificationScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalificationSchedule} and its DTO {@link MPrequalificationScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {MPrequalificationInformationMapper.class, ADOrganizationMapper.class, CPrequalificationEventLineMapper.class, MPrequalificationDateSetMapper.class})
public interface MPrequalificationScheduleMapper extends EntityMapper<MPrequalificationScheduleDTO, MPrequalificationSchedule> {

    @Mapping(source = "dateSet.id", target = "dateSetId")
    @Mapping(source = "dateSet.startDate", target = "actualStartDate")
    @Mapping(source = "dateSet.endDate", target = "actualEndDate")
    @Mapping(source = "dateSet.status", target = "status")
    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "eventLine.id", target = "eventLineId")
    @Mapping(source = "eventLine.prequalificationStep.name", target = "eventLineName")
    @Mapping(source = "eventLine.prequalificationStep.adForm.id", target = "adFormId")
    @Mapping(source = "eventLine.prequalificationStep.adForm.name", target = "adFormName")
    MPrequalificationScheduleDTO toDto(MPrequalificationSchedule mPrequalificationSchedule);

    @Mapping(source = "prequalificationId", target = "prequalification")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "eventLineId", target = "eventLine")
    @Mapping(source = "dateSetId", target = "dateSet")
    MPrequalificationSchedule toEntity(MPrequalificationScheduleDTO mPrequalificationScheduleDTO);

    default MPrequalificationSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalificationSchedule mPrequalificationSchedule = new MPrequalificationSchedule();
        mPrequalificationSchedule.setId(id);
        return mPrequalificationSchedule;
    }
}
