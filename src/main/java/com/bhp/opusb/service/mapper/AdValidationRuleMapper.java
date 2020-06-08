package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.AdValidationRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdValidationRule} and its DTO {@link AdValidationRuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface AdValidationRuleMapper extends EntityMapper<AdValidationRuleDTO, AdValidationRule> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    AdValidationRuleDTO toDto(AdValidationRule adValidationRule);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    AdValidationRule toEntity(AdValidationRuleDTO adValidationRuleDTO);

    default AdValidationRule fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdValidationRule adValidationRule = new AdValidationRule();
        adValidationRule.setId(id);
        return adValidationRule;
    }
}
