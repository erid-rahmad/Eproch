package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MProductPrice;
import com.bhp.opusb.service.dto.MProductPriceDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MProductPrice} and its DTO {@link MProductPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface MProductPriceMapper extends EntityMapper<MProductPriceDTO, MProductPrice> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    MProductPriceDTO toDto(MProductPrice mProductPrice);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    MProductPrice toEntity(MProductPriceDTO mProductPriceDTO);

    default MProductPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProductPrice mProductPrice = new MProductPrice();
        mProductPrice.setId(id);
        return mProductPrice;
    }
}
