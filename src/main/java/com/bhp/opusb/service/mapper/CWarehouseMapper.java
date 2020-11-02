package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CWarehouseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CWarehouse} and its DTO {@link CWarehouseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CWarehouseMapper extends EntityMapper<CWarehouseDTO, CWarehouse> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CWarehouseDTO toDto(CWarehouse cWarehouse);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CWarehouse toEntity(CWarehouseDTO cWarehouseDTO);

    default CWarehouse fromId(Long id) {
        if (id == null) {
            return null;
        }
        CWarehouse cWarehouse = new CWarehouse();
        cWarehouse.setId(id);
        return cWarehouse;
    }
}
