package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CBankDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CBank} and its DTO {@link CBankDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CBankMapper extends EntityMapper<CBankDTO, CBank> {



    default CBank fromId(Long id) {
        if (id == null) {
            return null;
        }
        CBank cBank = new CBank();
        cBank.setId(id);
        return cBank;
    }
}
