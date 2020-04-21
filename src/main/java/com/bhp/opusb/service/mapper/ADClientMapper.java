package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.ADClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ADClient} and its DTO {@link ADClientDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ADClientMapper extends EntityMapper<ADClientDTO, ADClient> {



    default ADClient fromId(Long id) {
        if (id == null) {
            return null;
        }
        ADClient aDClient = new ADClient();
        aDClient.setId(id);
        return aDClient;
    }
}
