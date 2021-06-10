package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.service.dto.MAuctionDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MAuction} and its DTO {@link MAuctionDTO}.
 */
@Mapper(componentModel = "spring", uses = {MAuctionRuleMapper.class, MAuctionContentMapper.class, ADOrganizationMapper.class, CCurrencyMapper.class, CCostCenterMapper.class, AdUserMapper.class, CAuctionPrerequisiteMapper.class})
public interface MAuctionMapper extends EntityMapper<MAuctionDTO, MAuction> {

    @Mapping(source = "rule.id", target = "ruleId")
    @Mapping(source = "rule.startDate", target = "ruleStartDate")
    @Mapping(source = "rule.bidImprovementUnit", target = "ruleBidImprovementUnit")
    @Mapping(source = "rule.tieBidsRule", target = "ruleTieBidsRule")
    @Mapping(source = "content.id", target = "contentId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.code", target = "currencyName")
    @Mapping(source = "costCenter.id", target = "costCenterId")
    @Mapping(source = "costCenter.name", target = "costCenterName")
    @Mapping(source = "owner.id", target = "ownerUserId")
    @Mapping(source = "owner.user.login", target = "ownerName")
    @Mapping(source = "prerequisite.id", target = "prerequisiteId")
    MAuctionDTO toDto(MAuction mAuction);

    @Mapping(source = "ruleId", target = "rule")
    @Mapping(source = "contentId", target = "content")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "currencyId", target = "currency")
    @Mapping(source = "costCenterId", target = "costCenter")
    @Mapping(source = "ownerUserId", target = "owner")
    @Mapping(source = "prerequisiteId", target = "prerequisite")
    MAuction toEntity(MAuctionDTO mAuctionDTO);

    default MAuction fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuction mAuction = new MAuction();
        mAuction.setId(id);
        return mAuction;
    }
}
