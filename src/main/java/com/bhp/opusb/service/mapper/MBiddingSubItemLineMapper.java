package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MBiddingSubItemLine;
import com.bhp.opusb.service.dto.MBiddingSubItemLineDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MBiddingSubItemLine} and its DTO {@link MBiddingSubItemLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CProductMapper.class, CUnitOfMeasureMapper.class, MBiddingSubItemMapper.class})
public interface MBiddingSubItemLineMapper extends EntityMapper<MBiddingSubItemLineDTO, MBiddingSubItemLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.name", target = "uomName")
    @Mapping(source = "biddingSubItem.id", target = "biddingSubItemId")
    MBiddingSubItemLineDTO toDto(MBiddingSubItemLine mBiddingSubItemLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "uomId", target = "uom")
    @Mapping(source = "biddingSubItemId", target = "biddingSubItem")
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
