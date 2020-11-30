package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AiExchangeInDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AiExchangeIn} and its DTO {@link AiExchangeInDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AiExchangeInMapper extends EntityMapper<AiExchangeInDTO, AiExchangeIn> {



    default AiExchangeIn fromId(Long id) {
        if (id == null) {
            return null;
        }
        AiExchangeIn aiExchangeIn = new AiExchangeIn();
        aiExchangeIn.setId(id);
        return aiExchangeIn;
    }
}
