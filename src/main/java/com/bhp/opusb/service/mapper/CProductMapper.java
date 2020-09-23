package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CProduct} and its DTO {@link CProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CProductClassificationMapper.class, CProductCategoryMapper.class, CProductCategoryAccountMapper.class, CUnitOfMeasureMapper.class})
public interface CProductMapper extends EntityMapper<CProductDTO, CProduct> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "productClassification.id", target = "productClassificationId")
    @Mapping(source = "productClassification.name", target = "productClassificationName")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    @Mapping(source = "productSubCategory.id", target = "productSubCategoryId")
    @Mapping(source = "productSubCategory.name", target = "productSubCategoryName")
    @Mapping(source = "assetAcct.id", target = "assetAcctId")
    @Mapping(source = "assetAcct.assetAcct.name", target = "assetAcctName")
    @Mapping(source = "expenseAcct.id", target = "expenseAcctId")
    @Mapping(source = "expenseAcct.expenseAcct.name", target = "expenseAcctName")
    @Mapping(source = "uom.id", target = "uomId")
    @Mapping(source = "uom.name", target = "uomName")
    CProductDTO toDto(CProduct cProduct);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "productClassificationId", target = "productClassification")
    @Mapping(source = "productCategoryId", target = "productCategory")
    @Mapping(source = "productSubCategoryId", target = "productSubCategory")
    @Mapping(source = "assetAcctId", target = "assetAcct")
    @Mapping(source = "expenseAcctId", target = "expenseAcct")
    @Mapping(source = "uomId", target = "uom")
    CProduct toEntity(CProductDTO cProductDTO);

    default CProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        CProduct cProduct = new CProduct();
        cProduct.setId(id);
        return cProduct;
    }
}
