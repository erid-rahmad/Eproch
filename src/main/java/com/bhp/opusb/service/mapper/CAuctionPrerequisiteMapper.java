package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.CAuctionPrerequisite;
import com.bhp.opusb.service.dto.CAuctionPrerequisiteDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link CAuctionPrerequisite} and its DTO {@link CAuctionPrerequisiteDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CAuctionPrerequisiteMapper extends EntityMapper<CAuctionPrerequisiteDTO, CAuctionPrerequisite> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CAuctionPrerequisiteDTO toDto(CAuctionPrerequisite cAuctionPrerequisite);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CAuctionPrerequisite toEntity(CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO);

    default CAuctionPrerequisite fromId(Long id) {
        if (id == null) {
            return null;
        }
        CAuctionPrerequisite cAuctionPrerequisite = new CAuctionPrerequisite();
        cAuctionPrerequisite.setId(id);
        return cAuctionPrerequisite;
    }
}
