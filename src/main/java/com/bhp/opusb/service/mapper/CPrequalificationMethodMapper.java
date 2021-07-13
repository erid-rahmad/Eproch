package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPrequalificationMethodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPrequalificationMethod} and its DTO {@link CPrequalificationMethodDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CPrequalificationMethodMapper extends EntityMapper<CPrequalificationMethodDTO, CPrequalificationMethod> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CPrequalificationMethodDTO toDto(CPrequalificationMethod cPrequalificationMethod);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CPrequalificationMethod toEntity(CPrequalificationMethodDTO cPrequalificationMethodDTO);

    default CPrequalificationMethod fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPrequalificationMethod cPrequalificationMethod = new CPrequalificationMethod();
        cPrequalificationMethod.setId(id);
        return cPrequalificationMethod;
    }
}
