package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CLocator;
import com.bhp.opusb.service.dto.CLocatorDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CLocator} and its DTO {@link CLocatorDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CWarehouseMapper.class})
public interface CLocatorMapper extends EntityMapper<CLocatorDTO, CLocator> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "warehouse.id", target = "warehouseId")
    @Mapping(source = "warehouse.name", target = "warehouseName")
    CLocatorDTO toDto(CLocator cLocator);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "warehouseId", target = "warehouse")
    CLocator toEntity(CLocatorDTO cLocatorDTO);

    default CLocator fromId(Long id) {
        if (id == null) {
            return null;
        }
        CLocator cLocator = new CLocator();
        cLocator.setId(id);
        return cLocator;
    }
}
