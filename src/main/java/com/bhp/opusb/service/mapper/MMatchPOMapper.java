package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MMatchPODTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MMatchPO} and its DTO {@link MMatchPODTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MMatchPOMapper extends EntityMapper<MMatchPODTO, MMatchPO> {



    default MMatchPO fromId(Long id) {
        if (id == null) {
            return null;
        }
        MMatchPO mMatchPO = new MMatchPO();
        mMatchPO.setId(id);
        return mMatchPO;
    }
}
