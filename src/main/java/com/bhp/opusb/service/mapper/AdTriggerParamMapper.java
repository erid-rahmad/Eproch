package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdTriggerParamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdTriggerParam} and its DTO {@link AdTriggerParamDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, ADReferenceMapper.class, AdValidationRuleMapper.class, AdTriggerMapper.class})
public interface AdTriggerParamMapper extends EntityMapper<AdTriggerParamDTO, AdTriggerParam> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "adReference.id", target = "adReferenceId")
    @Mapping(source = "adReference.name", target = "adReferenceName")
    @Mapping(source = "adValidationRule.id", target = "adValidationRuleId")
    @Mapping(source = "adValidationRule.name", target = "adValidationRuleName")
    @Mapping(source = "adTrigger.id", target = "adTriggerId")
    @Mapping(source = "adTrigger.name", target = "adTriggerName")
    AdTriggerParamDTO toDto(AdTriggerParam adTriggerParam);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "adReferenceId", target = "adReference")
    @Mapping(source = "adValidationRuleId", target = "adValidationRule")
    @Mapping(source = "adTriggerId", target = "adTrigger")
    AdTriggerParam toEntity(AdTriggerParamDTO adTriggerParamDTO);

    default AdTriggerParam fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdTriggerParam adTriggerParam = new AdTriggerParam();
        adTriggerParam.setId(id);
        return adTriggerParam;
    }
}
