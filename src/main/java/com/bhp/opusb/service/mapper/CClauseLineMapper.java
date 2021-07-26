package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CClauseLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CClauseLine} and its DTO {@link CClauseLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {CClauseMapper.class, ADOrganizationMapper.class})
public interface CClauseLineMapper extends EntityMapper<CClauseLineDTO, CClauseLine> {

    @Mapping(source = "clause.id", target = "clauseId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CClauseLineDTO toDto(CClauseLine cClauseLine);

    @Mapping(source = "clauseId", target = "clause")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CClauseLine toEntity(CClauseLineDTO cClauseLineDTO);

    default CClauseLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        CClauseLine cClauseLine = new CClauseLine();
        cClauseLine.setId(id);
        return cClauseLine;
    }
}
