package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPaymentScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPaymentSchedule} and its DTO {@link CPaymentScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CPaymentTermMapper.class})
public interface CPaymentScheduleMapper extends EntityMapper<CPaymentScheduleDTO, CPaymentSchedule> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "CPaymentTerm.id", target = "CPaymentTermId")
    @Mapping(source = "CPaymentTerm.name", target = "CPaymentTermName")
    CPaymentScheduleDTO toDto(CPaymentSchedule cPaymentSchedule);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "CPaymentTermId", target = "CPaymentTerm")
    CPaymentSchedule toEntity(CPaymentScheduleDTO cPaymentScheduleDTO);

    default CPaymentSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPaymentSchedule cPaymentSchedule = new CPaymentSchedule();
        cPaymentSchedule.setId(id);
        return cPaymentSchedule;
    }
}
