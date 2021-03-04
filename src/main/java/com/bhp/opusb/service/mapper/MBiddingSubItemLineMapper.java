package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingSubItemLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingSubItemLine} and its DTO {@link MBiddingSubItemLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingSubItemMapper.class, CProductMapper.class, CUnitOfMeasureMapper.class})
public interface MBiddingSubItemLineMapper extends EntityMapper<MBiddingSubItemLineDTO, MBiddingSubItemLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingSubItem.id", target = "biddingSubItemId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.name", target = "uomName")
    MBiddingSubItemLineDTO toDto(MBiddingSubItemLine mBiddingSubItemLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingSubItemId", target = "biddingSubItem")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "uomId", target = "uom")
    MBiddingSubItemLine toEntity(MBiddingSubItemLineDTO mBiddingSubItemLineDTO);

    default MBiddingSubItemLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingSubItemLine mBiddingSubItemLine = new MBiddingSubItemLine();
        mBiddingSubItemLine.setId(id);
        return mBiddingSubItemLine;
    }
}
