package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CCurrencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CCurrency} and its DTO {@link CCurrencyDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CCurrencyMapper extends EntityMapper<CCurrencyDTO, CCurrency> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CCurrencyDTO toDto(CCurrency cCurrency);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CCurrency toEntity(CCurrencyDTO cCurrencyDTO);

    default CCurrency fromId(Long id) {
        if (id == null) {
            return null;
        }
        CCurrency cCurrency = new CCurrency();
        cCurrency.setId(id);
        return cCurrency;
    }
}
