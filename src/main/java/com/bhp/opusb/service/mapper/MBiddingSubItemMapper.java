package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingSubItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingSubItem} and its DTO {@link MBiddingSubItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingLineMapper.class, CProductMapper.class})
public interface MBiddingSubItemMapper extends EntityMapper<MBiddingSubItemDTO, MBiddingSubItem> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingLine.id", target = "biddingLineId")
    @Mapping(source = "biddingLine.product.name", target = "biddingLineName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    MBiddingSubItemDTO toDto(MBiddingSubItem mBiddingSubItem);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingLineId", target = "biddingLine")
    @Mapping(source = "productId", target = "product")
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
