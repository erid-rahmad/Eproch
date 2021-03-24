package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MDocumentScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MDocumentSchedule} and its DTO {@link MDocumentScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, ADOrganizationMapper.class, MBiddingScheduleMapper.class})
public interface MDocumentScheduleMapper extends EntityMapper<MDocumentScheduleDTO, MDocumentSchedule> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "vendorSubmission.id", target = "vendorSubmissionId")
    @Mapping(source = "vendorSubmission.startDate", target = "vendorSubmissionStartDate")
    @Mapping(source = "vendorSubmission.endDate", target = "vendorSubmissionEndDate")
    @Mapping(source = "vendorEvaluation.id", target = "vendorEvaluationId")
    MDocumentScheduleDTO toDto(MDocumentSchedule mDocumentSchedule);

    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "vendorSubmissionId", target = "vendorSubmission")
    @Mapping(source = "vendorEvaluationId", target = "vendorEvaluation")
    MDocumentSchedule toEntity(MDocumentScheduleDTO mDocumentScheduleDTO);

    default MDocumentSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        MDocumentSchedule mDocumentSchedule = new MDocumentSchedule();
        mDocumentSchedule.setId(id);
        return mDocumentSchedule;
    }
}
