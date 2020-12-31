package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MShoppingCartItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MShoppingCartItem} and its DTO {@link MShoppingCartItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CProductMapper.class, MShoppingCartMapper.class})
public interface MShoppingCartItemMapper extends EntityMapper<MShoppingCartItemDTO, MShoppingCartItem> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "mProduct.id", target = "mProductId")
    @Mapping(source = "mShoppingCart.id", target = "mShoppingCartId")
    MShoppingCartItemDTO toDto(MShoppingCartItem mShoppingCartItem);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "mProductId", target = "mProduct")
    @Mapping(source = "mShoppingCartId", target = "mShoppingCart")
    MShoppingCartItem toEntity(MShoppingCartItemDTO mShoppingCartItemDTO);

    default MShoppingCartItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        MShoppingCartItem mShoppingCartItem = new MShoppingCartItem();
        mShoppingCartItem.setId(id);
        return mShoppingCartItem;
    }
}
