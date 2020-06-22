package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CCurrencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CCurrency} and its DTO {@link CCurrencyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CCurrencyMapper extends EntityMapper<CCurrencyDTO, CCurrency> {



    default CCurrency fromId(Long id) {
        if (id == null) {
            return null;
        }
        CCurrency cCurrency = new CCurrency();
        cCurrency.setId(id);
        return cCurrency;
    }
}
