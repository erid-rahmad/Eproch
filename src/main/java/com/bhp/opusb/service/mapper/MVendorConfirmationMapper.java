package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MVendorConfirmation;
import com.bhp.opusb.service.dto.MVendorConfirmationDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MVendorConfirmation} and its DTO {@link MVendorConfirmationDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, ADOrganizationMapper.class, CCurrencyMapper.class, CCostCenterMapper.class, AdUserMapper.class})
public interface MVendorConfirmationMapper extends EntityMapper<MVendorConfirmationDTO, MVendorConfirmation> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.documentNo", target = "biddingNo")
    @Mapping(source = "bidding.requisition.warehouse.id", target = "warehouseId")
    @Mapping(source = "bidding.name", target = "biddingTitle")
    @Mapping(source = "bidding.biddingType.name", target = "biddingTypeName")
    @Mapping(source = "bidding.biddingStatus", target = "biddingStatus")
    @Mapping(source = "bidding.ceilingPrice", target = "amount")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.code", target = "currencyName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    @Mapping(source = "pic.id", target = "picId")
    @Mapping(source = "pic.user.login", target = "picName")
    MVendorConfirmationDTO toDto(MVendorConfirmation mVendorConfirmation);

    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "picId", target = "pic")
    MVendorConfirmation toEntity(MVendorConfirmationDTO mVendorConfirmationDTO);

    default MVendorConfirmation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MVendorConfirmation mVendorConfirmation = new MVendorConfirmation();
        mVendorConfirmation.setId(id);
        return mVendorConfirmation;
    }
}
