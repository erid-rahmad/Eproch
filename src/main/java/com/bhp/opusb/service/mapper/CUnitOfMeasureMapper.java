package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CUnitOfMeasure;
import com.bhp.opusb.service.dto.CUnitOfMeasureDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CUnitOfMeasure} and its DTO {@link CUnitOfMeasureDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CUnitOfMeasureMapper extends EntityMapper<CUnitOfMeasureDTO, CUnitOfMeasure> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CUnitOfMeasureDTO toDto(CUnitOfMeasure cUnitOfMeasure);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CUnitOfMeasure toEntity(CUnitOfMeasureDTO cUnitOfMeasureDTO);

    default CUnitOfMeasure fromId(Long id) {
        if (id == null) {
            return null;
        }
        CUnitOfMeasure cUnitOfMeasure = new CUnitOfMeasure();
        cUnitOfMeasure.setId(id);
        return cUnitOfMeasure;
    }
}
