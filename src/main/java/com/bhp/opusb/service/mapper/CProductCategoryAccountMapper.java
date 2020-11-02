package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CProductCategoryAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CProductCategoryAccount} and its DTO {@link CProductCategoryAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CElementValueMapper.class, CProductCategoryMapper.class})
public interface CProductCategoryAccountMapper extends EntityMapper<CProductCategoryAccountDTO, CProductCategoryAccount> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "assetAcct.id", target = "assetAcctId")
    @Mapping(source = "assetAcct.name", target = "assetAcctName")
    @Mapping(source = "expenseAcct.id", target = "expenseAcctId")
    @Mapping(source = "expenseAcct.name", target = "expenseAcctName")
    @Mapping(source = "productCategory.id", target = "productCategoryId")
    @Mapping(source = "productCategory.name", target = "productCategoryName")
    CProductCategoryAccountDTO toDto(CProductCategoryAccount cProductCategoryAccount);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "assetAcctId", target = "assetAcct")
    @Mapping(source = "expenseAcctId", target = "expenseAcct")
    @Mapping(source = "productCategoryId", target = "productCategory")
    CProductCategoryAccount toEntity(CProductCategoryAccountDTO cProductCategoryAccountDTO);

    default CProductCategoryAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        CProductCategoryAccount cProductCategoryAccount = new CProductCategoryAccount();
        cProductCategoryAccount.setId(id);
        return cProductCategoryAccount;
    }
}
