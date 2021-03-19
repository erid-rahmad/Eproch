package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MBiddingSubItem;
import com.bhp.opusb.service.dto.MBiddingSubItemDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MBiddingSubItem} and its DTO {@link MBiddingSubItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CProductMapper.class})
public interface MBiddingSubItemMapper extends EntityMapper<MBiddingSubItemDTO, MBiddingSubItem> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    MBiddingSubItemDTO toDto(MBiddingSubItem mBiddingSubItem);

    @Mapping(target = "mBiddingSubItemLines", ignore = true)
    @Mapping(target = "removeMBiddingSubItemLine", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "productId", target = "product")
    @Mapping(target = "biddingLine", ignore = true)
    MBiddingSubItem toEntity(MBiddingSubItemDTO mBiddingSubItemDTO);

    default MBiddingSubItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingSubItem mBiddingSubItem = new MBiddingSubItem();
        mBiddingSubItem.setId(id);
        return mBiddingSubItem;
    }
}
