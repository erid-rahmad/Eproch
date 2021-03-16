package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPaymentTermDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPaymentTerm} and its DTO {@link CPaymentTermDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CPaymentTermMapper extends EntityMapper<CPaymentTermDTO, CPaymentTerm> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CPaymentTermDTO toDto(CPaymentTerm cPaymentTerm);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CPaymentTerm toEntity(CPaymentTermDTO cPaymentTermDTO);

    default CPaymentTerm fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPaymentTerm cPaymentTerm = new CPaymentTerm();
        cPaymentTerm.setId(id);
        return cPaymentTerm;
    }
}
