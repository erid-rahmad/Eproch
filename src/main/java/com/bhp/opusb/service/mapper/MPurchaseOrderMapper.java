package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPurchaseOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPurchaseOrder} and its DTO {@link MPurchaseOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CDocumentTypeMapper.class, CVendorMapper.class, CCurrencyMapper.class, CWarehouseMapper.class, CCostCenterMapper.class})
public interface MPurchaseOrderMapper extends EntityMapper<MPurchaseOrderDTO, MPurchaseOrder> {



    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.name", target = "documentTypeName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.code", target = "currencyName")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "warehouse.name", target = "warehouseName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    MPurchaseOrderDTO toDto(MPurchaseOrder mPurchaseOrder);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "documentTypeId", target = "documentType")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "costCenterId", target = "costCenter")
    MPurchaseOrder toEntity(MPurchaseOrderDTO mPurchaseOrderDTO);

    default MPurchaseOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPurchaseOrder mPurchaseOrder = new MPurchaseOrder();
        mPurchaseOrder.setId(id);
        return mPurchaseOrder;
    }
}
