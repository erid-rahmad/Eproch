package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPrequalMethodLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPrequalMethodLine} and its DTO {@link CPrequalMethodLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CPrequalificationMethodMapper.class})
public interface CPrequalMethodLineMapper extends EntityMapper<CPrequalMethodLineDTO, CPrequalMethodLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "prequalMethod.id", target = "prequalMethodId")
    CPrequalMethodLineDTO toDto(CPrequalMethodLine cPrequalMethodLine);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "prequalMethodId", target = "prequalMethod")
    CPrequalMethodLine toEntity(CPrequalMethodLineDTO cPrequalMethodLineDTO);

    default CPrequalMethodLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPrequalMethodLine cPrequalMethodLine = new CPrequalMethodLine();
        cPrequalMethodLine.setId(id);
        return cPrequalMethodLine;
    }
}
