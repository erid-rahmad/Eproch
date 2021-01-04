package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MProductCatalogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MProductCatalog} and its DTO {@link MProductCatalogDTO}.
 */
@Mapper(componentModel = "spring", uses = {CGalleryMapper.class, ADOrganizationMapper.class, CDocumentTypeMapper.class, CCurrencyMapper.class, CUnitOfMeasureMapper.class, CVendorMapper.class, MBrandMapper.class, CProductMapper.class})
public interface MProductCatalogMapper extends EntityMapper<MProductCatalogDTO, MProductCatalog> {

    @Mapping(source = "CGallery.id", target = "CGalleryId")
    @Mapping(source = "CGallery.name", target = "CGalleryName")
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
    @Mapping(source = "MBrand.id", target = "MBrandId")
    @Mapping(source = "MBrand.name", target = "MBrandName")
    @Mapping(source = "MProduct.id", target = "MProductId")
    @Mapping(source = "MProduct.code", target = "MProductCode")
    @Mapping(source = "MProduct.name", target = "MProductShortName")
    @Mapping(source = "MProduct.description", target = "MProductDescription")
    MProductCatalogDTO toDto(MProductCatalog mProductCatalog);

    @Mapping(source = "CGalleryId", target = "CGallery")
    @Mapping(target = "MProductPrices", ignore = true)
    @Mapping(target = "removeMProductPrice", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "CDocumentTypeId", target = "CDocumentType")
    @Mapping(source = "CCurrencyId", target = "CCurrency")
    @Mapping(source = "CUomId", target = "CUom")
    @Mapping(source = "CVendorId", target = "CVendor")
    @Mapping(source = "MBrandId", target = "MBrand")
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
