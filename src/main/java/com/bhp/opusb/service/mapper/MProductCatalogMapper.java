package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MProductCatalogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MProductCatalog} and its DTO {@link MProductCatalogDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CDocumentTypeMapper.class, CCurrencyMapper.class, CUnitOfMeasureMapper.class, CVendorMapper.class, CProductMapper.class})
public interface MProductCatalogMapper extends EntityMapper<MProductCatalogDTO, MProductCatalog> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "cDocumentType.id", target = "cDocumentTypeId")
    @Mapping(source = "cCurrency.id", target = "cCurrencyId")
    @Mapping(source = "cUom.id", target = "cUomId")
    @Mapping(source = "cVendor.id", target = "cVendorId")
    @Mapping(source = "mProduct.id", target = "mProductId")
    MProductCatalogDTO toDto(MProductCatalog mProductCatalog);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "cDocumentTypeId", target = "cDocumentType")
    @Mapping(source = "cCurrencyId", target = "cCurrency")
    @Mapping(source = "cUomId", target = "cUom")
    @Mapping(source = "cVendorId", target = "cVendor")
    @Mapping(source = "mProductId", target = "mProduct")
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
