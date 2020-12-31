package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MProductPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MProductPrice} and its DTO {@link MProductPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MProductPriceMapper extends EntityMapper<MProductPriceDTO, MProductPrice> {



    default MProductPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProductPrice mProductPrice = new MProductPrice();
        mProductPrice.setId(id);
        return mProductPrice;
    }
}
