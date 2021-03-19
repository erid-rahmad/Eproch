package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MBiddingFormDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MBidding} and its DTO {@link MBiddingDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CCostCenterMapper.class, CCurrencyMapper.class, MRequisitionMapper.class, CDocumentTypeMapper.class, CBiddingTypeMapper.class, CEventTypeMapper.class, AdUserMapper.class})
public interface MBiddingFormMapper extends EntityMapper<MBiddingFormDTO, MBidding> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.code", target = "currencyName")
    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "requisition.documentNo", target = "requisitionName")
    @Mapping(source = "referenceType.id", target = "referenceTypeId")
    @Mapping(source = "referenceType.name", target = "referenceTypeName")
    @Mapping(source = "biddingType.id", target = "biddingTypeId")
    @Mapping(source = "biddingType.name", target = "biddingTypeName")
    @Mapping(source = "eventType.id", target = "eventTypeId")
    @Mapping(source = "eventType.name", target = "eventTypeName")
    @Mapping(source = "adUser.user.id", target = "adUserUserId")
    @Mapping(source = "adUser.user.login", target = "adUserUserName")
    MBiddingFormDTO toDto(MBidding mBidding);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "requisitionId", target = "requisition")
    @Mapping(source = "referenceTypeId", target = "referenceType")
    @Mapping(source = "biddingTypeId", target = "biddingType")
    @Mapping(source = "eventTypeId", target = "eventType")
    @Mapping(source = "adUserUserId", target = "adUser")
    MBidding toEntity(MBiddingFormDTO mBiddingDTO);

    default MBidding fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBidding mBidding = new MBidding();
        mBidding.setId(id);
        return mBidding;
    }
}
