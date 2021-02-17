package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBidding} and its DTO {@link MBiddingDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CCostCenterMapper.class, MRequisitionMapper.class, CBiddingTypeMapper.class, CEventTypeMapper.class, AdUserMapper.class})
public interface MBiddingMapper extends EntityMapper<MBiddingDTO, MBidding> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "biddingType.id", target = "biddingTypeId")
    @Mapping(source = "biddingType.name", target = "biddingTypeName")
    @Mapping(source = "eventType.id", target = "eventTypeId")
    @Mapping(source = "eventType.name", target = "eventTypeName")
    @Mapping(source = "adUser.user.id", target = "adUserUserId")
    @Mapping(source = "adUser.user.login", target = "adUserUserName")
    MBiddingDTO toDto(MBidding mBidding);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "requisitionId", target = "requisition")
    @Mapping(source = "biddingTypeId", target = "biddingType")
    @Mapping(source = "eventTypeId", target = "eventType")
    @Mapping(source = "adUserUserId", target = "adUser")
    MBidding toEntity(MBiddingDTO mBiddingDTO);

    default MBidding fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBidding mBidding = new MBidding();
        mBidding.setId(id);
        return mBidding;
    }
}
