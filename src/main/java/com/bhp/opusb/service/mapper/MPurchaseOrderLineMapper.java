package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPurchaseOrderLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPurchaseOrderLine} and its DTO {@link MPurchaseOrderLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MPurchaseOrderMapper.class, MRequisitionMapper.class, CTaxMapper.class, ADOrganizationMapper.class, CProductMapper.class, CWarehouseMapper.class, CCostCenterMapper.class, CUnitOfMeasureMapper.class, CVendorMapper.class})
public interface MPurchaseOrderLineMapper extends EntityMapper<MPurchaseOrderLineDTO, MPurchaseOrderLine> {

    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "tax.id", target = "taxId")
    @Mapping(source = "tax.name", target = "taxName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "warehouse.name", target = "warehouseName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.name", target = "uomName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MPurchaseOrderLineDTO toDto(MPurchaseOrderLine mPurchaseOrderLine);

    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    @Mapping(source = "requisitionId", target = "requisition")
    @Mapping(source = "taxId", target = "tax")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "uomId", target = "uom")
    @Mapping(source = "vendorId", target = "vendor")
    MPurchaseOrderLine toEntity(MPurchaseOrderLineDTO mPurchaseOrderLineDTO);

    default MPurchaseOrderLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPurchaseOrderLine mPurchaseOrderLine = new MPurchaseOrderLine();
        mPurchaseOrderLine.setId(id);
        return mPurchaseOrderLine;
    }
}
