package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingLine} and its DTO {@link MBiddingLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, ADOrganizationMapper.class, CCostCenterMapper.class, CProductMapper.class, CUnitOfMeasureMapper.class})
public interface MBiddingLineMapper extends EntityMapper<MBiddingLineDTO, MBiddingLine> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.name", target = "uomName")
    MBiddingLineDTO toDto(MBiddingLine mBiddingLine);

    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "uomId", target = "uom")
    MBiddingLine toEntity(MBiddingLineDTO mBiddingLineDTO);

    default MBiddingLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingLine mBiddingLine = new MBiddingLine();
        mBiddingLine.setId(id);
        return mBiddingLine;
    }
}
