package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CompanyFunctionaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyFunctionary} and its DTO {@link CompanyFunctionaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {VendorMapper.class})
public interface CompanyFunctionaryMapper extends EntityMapper<CompanyFunctionaryDTO, CompanyFunctionary> {

    @Mapping(source = "vendor.id", target = "vendorId")
    CompanyFunctionaryDTO toDto(CompanyFunctionary companyFunctionary);

    @Mapping(source = "vendorId", target = "vendor")
    CompanyFunctionary toEntity(CompanyFunctionaryDTO companyFunctionaryDTO);

    default CompanyFunctionary fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyFunctionary companyFunctionary = new CompanyFunctionary();
        companyFunctionary.setId(id);
        return companyFunctionary;
    }
}
