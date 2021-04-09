package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CBudgetPlanLine;
import com.bhp.opusb.service.dto.CBudgetPlanLineDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CBudgetPlanLine} and its DTO {@link CBudgetPlanLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CCurrencyMapper.class, CDocumentTypeMapper.class, CBudgetPlanMapper.class, MBiddingMapper.class, MPurchaseOrderMapper.class, MRequisitionMapper.class})
public interface CBudgetPlanLineMapper extends EntityMapper<CBudgetPlanLineDTO, CBudgetPlanLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "CCurrency.id", target = "CCurrencyId")
    @Mapping(source = "CCurrency.code", target = "CCurrencyName")
    @Mapping(source = "CDocumentType.id", target = "CDocumentTypeId")
    @Mapping(source = "CDocumentType.name", target = "CDocumentTypeName")
    @Mapping(source = "CBudgetPlan.id", target = "CBudgetPlanId")
    @Mapping(source = "CBudgetPlan.name", target = "CBudgetPlanName")
    @Mapping(source = "MBidding.id", target = "MBiddingId")
    @Mapping(source = "MBidding.documentNo", target = "MBiddingName")
    @Mapping(source = "MPurchaseOrder.id", target = "MPurchaseOrderId")
    @Mapping(source = "MPurchaseOrder.documentNo", target = "MPurchaseOrderName")
    @Mapping(source = "MRequisition.id", target = "MRequisitionId")
    @Mapping(source = "MRequisition.documentNo", target = "MRequisitionName")
    CBudgetPlanLineDTO toDto(CBudgetPlanLine cBudgetPlanLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "CCurrencyId", target = "CCurrency")
    @Mapping(source = "CDocumentTypeId", target = "CDocumentType")
    @Mapping(source = "CBudgetPlanId", target = "CBudgetPlan")
    @Mapping(source = "MBiddingId", target = "mBidding")
    @Mapping(source = "MPurchaseOrderId", target = "mPurchaseOrder")
    @Mapping(source = "MRequisitionId", target = "mRequisition")
    CBudgetPlanLine toEntity(CBudgetPlanLineDTO cBudgetPlanLineDTO);

    default CBudgetPlanLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CBudgetPlanLine cBudgetPlanLine = new CBudgetPlanLine();
        cBudgetPlanLine.setId(id);
        return cBudgetPlanLine;
    }
}
