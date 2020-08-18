package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CFunctionary;
import com.bhp.opusb.service.dto.CFunctionaryDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CFunctionary} and its DTO {@link CFunctionaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CVendorMapper.class})
public interface CFunctionaryMapper extends EntityMapper<CFunctionaryDTO, CFunctionary> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    CFunctionaryDTO toDto(CFunctionary cFunctionary);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "vendorId", target = "vendor")
    CFunctionary toEntity(CFunctionaryDTO cFunctionaryDTO);

    default CFunctionary fromId(Long id) {
        if (id == null) {
            return null;
        }
        CFunctionary cFunctionary = new CFunctionary();
        cFunctionary.setId(id);
        return cFunctionary;
    }
}
