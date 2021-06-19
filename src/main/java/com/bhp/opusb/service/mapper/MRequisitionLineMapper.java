package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MRequisitionLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MRequisitionLine} and its DTO {@link MRequisitionLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MRequisitionMapper.class, ADOrganizationMapper.class, CProductMapper.class, CWarehouseMapper.class, CCostCenterMapper.class, CUnitOfMeasureMapper.class, CVendorMapper.class})
public interface MRequisitionLineMapper extends EntityMapper<MRequisitionLineDTO, MRequisitionLine> {

    @Mapping(source = "requisition.id", target = "requisitionId")
    @Mapping(source = "requisition.documentNo", target = "requisitionName")
    @Mapping(source = "requisition.documentType.name", target = "requisitionType")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "warehouse.code", target = "warehouseName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.code", target = "costCenterName")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.code", target = "uomName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MRequisitionLineDTO toDto(MRequisitionLine mRequisitionLine);

    @Mapping(source = "requisitionId", target = "requisition")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "uomId", target = "uom")
    @Mapping(source = "vendorId", target = "vendor")
    MRequisitionLine toEntity(MRequisitionLineDTO mRequisitionLineDTO);

    default MRequisitionLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MRequisitionLine mRequisitionLine = new MRequisitionLine();
        mRequisitionLine.setId(id);
        return mRequisitionLine;
    }
}
