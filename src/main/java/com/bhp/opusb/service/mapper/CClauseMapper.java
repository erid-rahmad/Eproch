package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CClauseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CClause} and its DTO {@link CClauseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CClauseMapper extends EntityMapper<CClauseDTO, CClause> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CClauseDTO toDto(CClause cClause);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CClause toEntity(CClauseDTO cClauseDTO);

    default CClause fromId(Long id) {
        if (id == null) {
            return null;
        }
        CClause cClause = new CClause();
        cClause.setId(id);
        return cClause;
    }
}
