package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuctionRule;
import com.bhp.opusb.service.dto.MAuctionRuleDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuctionRule} and its DTO {@link MAuctionRuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface MAuctionRuleMapper extends EntityMapper<MAuctionRuleDTO, MAuctionRule> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    MAuctionRuleDTO toDto(MAuctionRule mAuctionRule);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    MAuctionRule toEntity(MAuctionRuleDTO mAuctionRuleDTO);

    default MAuctionRule fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuctionRule mAuctionRule = new MAuctionRule();
        mAuctionRule.setId(id);
        return mAuctionRule;
    }
}
