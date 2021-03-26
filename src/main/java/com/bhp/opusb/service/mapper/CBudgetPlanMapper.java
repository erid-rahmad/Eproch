package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CBudgetPlan;
import com.bhp.opusb.service.dto.CBudgetPlanDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CBudgetPlan} and its DTO {@link CBudgetPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CCostCenterMapper.class, CCurrencyMapper.class})
public interface CBudgetPlanMapper extends EntityMapper<CBudgetPlanDTO, CBudgetPlan> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "CCostCenter.id", target = "CCostCenterId")
    @Mapping(source = "CCostCenter.name", target = "CCostCenterName")
    @Mapping(source = "CCurrency.id", target = "CCurrencyId")
    @Mapping(source = "CCurrency.code", target = "CCurrencyName")
    CBudgetPlanDTO toDto(CBudgetPlan cBudgetPlan);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "CCostCenterId", target = "CCostCenter")
    @Mapping(source = "CCurrencyId", target = "CCurrency")
    CBudgetPlan toEntity(CBudgetPlanDTO cBudgetPlanDTO);

    default CBudgetPlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        CBudgetPlan cBudgetPlan = new CBudgetPlan();
        cBudgetPlan.setId(id);
        return cBudgetPlan;
    }
}
