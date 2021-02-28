package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AiExchangeOutDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AiExchangeOut} and its DTO {@link AiExchangeOutDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AiExchangeOutMapper extends EntityMapper<AiExchangeOutDTO, AiExchangeOut> {



    default AiExchangeOut fromId(Long id) {
        if (id == null) {
            return null;
        }
        AiExchangeOut aiExchangeOut = new AiExchangeOut();
        aiExchangeOut.setId(id);
        return aiExchangeOut;
    }
}
