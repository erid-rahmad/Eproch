package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CProductGroupAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CProductGroupAccount} and its DTO {@link CProductGroupAccountDTO}.
 */
@Mapper(componentModel = "spring", uses = {CElementValueMapper.class, CProductGroupMapper.class, ADOrganizationMapper.class})
public interface CProductGroupAccountMapper extends EntityMapper<CProductGroupAccountDTO, CProductGroupAccount> {

    @Mapping(source = "assetAccount.id", target = "assetAccountId")
    @Mapping(source = "assetAccount.name", target = "assetAccountName")
    @Mapping(source = "depreciationAccount.id", target = "depreciationAccountId")
    @Mapping(source = "depreciationAccount.name", target = "depreciationAccountName")
    @Mapping(source = "productGroup.id", target = "productGroupId")
    @Mapping(source = "productGroup.name", target = "productGroupName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CProductGroupAccountDTO toDto(CProductGroupAccount cProductGroupAccount);

    @Mapping(source = "assetAccountId", target = "assetAccount")
    @Mapping(source = "depreciationAccountId", target = "depreciationAccount")
    @Mapping(source = "productGroupId", target = "productGroup")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CProductGroupAccount toEntity(CProductGroupAccountDTO cProductGroupAccountDTO);

    default CProductGroupAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        CProductGroupAccount cProductGroupAccount = new CProductGroupAccount();
        cProductGroupAccount.setId(id);
        return cProductGroupAccount;
    }
}
