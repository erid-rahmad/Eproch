package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MRequisitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MRequisition} and its DTO {@link MRequisitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CDocumentTypeMapper.class, CCurrencyMapper.class, CWarehouseMapper.class, CCostCenterMapper.class})
public interface MRequisitionMapper extends EntityMapper<MRequisitionDTO, MRequisition> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.name", target = "documentTypeName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.code", target = "currencyName")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "warehouse.code", target = "warehouseName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.code", target = "costCenterName")
    MRequisitionDTO toDto(MRequisition mRequisition);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "documentTypeId", target = "documentType")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "warehouseId", target = "warehouse")
    @Mapping(source = "costCenterId", target = "costCenter")
    MRequisition toEntity(MRequisitionDTO mRequisitionDTO);

    default MRequisition fromId(Long id) {
        if (id == null) {
            return null;
        }
        MRequisition mRequisition = new MRequisition();
        mRequisition.setId(id);
        return mRequisition;
    }
}
