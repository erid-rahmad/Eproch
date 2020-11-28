package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MMatchPO;
import com.bhp.opusb.service.dto.MMatchPODTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MMatchPO} and its DTO {@link MMatchPODTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CCostCenterMapper.class, CVendorMapper.class, CCurrencyMapper.class, CTaxCategoryMapper.class, CUnitOfMeasureMapper.class, CProductMapper.class, CWarehouseMapper.class, CLocatorMapper.class})
public interface MMatchPOMapper extends EntityMapper<MMatchPODTO, MMatchPO> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "CCostCenter.id", target = "CCostCenterId")
    @Mapping(source = "CCostCenter.code", target = "CCostCenterName")
    @Mapping(source = "CVendor.id", target = "CVendorId")
    @Mapping(source = "CVendor.code", target = "CVendorCode")
    @Mapping(source = "CVendor.name", target = "CVendorName")
    @Mapping(source = "CCurrency.id", target = "CCurrencyId")
    @Mapping(source = "CCurrency.code", target = "CCurrencyName")
    @Mapping(source = "CTaxCategory.id", target = "CTaxCategoryId")
    @Mapping(source = "CTaxCategory.name", target = "CTaxCategoryName")
    @Mapping(source = "CUom.id", target = "CUomId")
    @Mapping(source = "CUom.code", target = "CUomName")
    @Mapping(source = "MProduct.id", target = "MProductId")
    @Mapping(source = "MProduct.code", target = "MProductCode")
    @Mapping(source = "MProduct.name", target = "MProductShortName")
    @Mapping(source = "MProduct.description", target = "MProductDescription")
    @Mapping(source = "MWarehouse.id", target = "MWarehouseId")
    @Mapping(source = "MWarehouse.code", target = "MWarehouseName")
    @Mapping(source = "MLocator.id", target = "MLocatorId")
    @Mapping(source = "MLocator.code", target = "MLocatorName")
    MMatchPODTO toDto(MMatchPO mMatchPO);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "CCostCenterId", target = "CCostCenter")
    @Mapping(source = "CVendorId", target = "CVendor")
    @Mapping(source = "CCurrencyId", target = "CCurrency")
    @Mapping(source = "CTaxCategoryId", target = "CTaxCategory")
    @Mapping(source = "CUomId", target = "CUom")
    @Mapping(source = "MProductId", target = "MProduct")
    @Mapping(source = "MWarehouseId", target = "MWarehouse")
    @Mapping(source = "MLocatorId", target = "MLocator")
    MMatchPO toEntity(MMatchPODTO mMatchPODTO);

    default MMatchPO fromId(Long id) {
        if (id == null) {
            return null;
        }
        MMatchPO mMatchPO = new MMatchPO();
        mMatchPO.setId(id);
        return mMatchPO;
    }
}
