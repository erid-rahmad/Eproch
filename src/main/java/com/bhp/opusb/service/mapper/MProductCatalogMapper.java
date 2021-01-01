package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MProductCatalog;
import com.bhp.opusb.service.dto.MProductCatalogDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MProductCatalog} and its DTO {@link MProductCatalogDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CDocumentTypeMapper.class, CCurrencyMapper.class, CUnitOfMeasureMapper.class, CVendorMapper.class, CProductMapper.class})
public interface MProductCatalogMapper extends EntityMapper<MProductCatalogDTO, MProductCatalog> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "CDocumentType.id", target = "CDocumentTypeId")
    @Mapping(source = "CDocumentType.name", target = "CDocumentTypeName")
    @Mapping(source = "CCurrency.id", target = "CCurrencyId")
    @Mapping(source = "CCurrency.code", target = "CCurrencyName")
    @Mapping(source = "CUom.id", target = "CUomId")
    @Mapping(source = "CUom.name", target = "CUomName")
    @Mapping(source = "CVendor.id", target = "CVendorId")
    @Mapping(source = "CVendor.name", target = "CVendorName")
    @Mapping(source = "MProduct.id", target = "MProductId")
    @Mapping(source = "MProduct.code", target = "MProductCode")
    @Mapping(source = "MProduct.name", target = "MProductShortName")
    @Mapping(source = "MProduct.description", target = "MProductDescription")
    MProductCatalogDTO toDto(MProductCatalog mProductCatalog);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "CDocumentTypeId", target = "CDocumentType")
    @Mapping(source = "CCurrencyId", target = "CCurrency")
    @Mapping(source = "CUomId", target = "CUom")
    @Mapping(source = "CVendorId", target = "CVendor")
    @Mapping(source = "MProductId", target = "MProduct")
    MProductCatalog toEntity(MProductCatalogDTO mProductCatalogDTO);

    default MProductCatalog fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProductCatalog mProductCatalog = new MProductCatalog();
        mProductCatalog.setId(id);
        return mProductCatalog;
    }
}
