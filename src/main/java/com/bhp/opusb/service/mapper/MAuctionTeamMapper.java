package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MAuctionTeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MAuctionTeam} and its DTO {@link MAuctionTeamDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdUserMapper.class, MAuctionMapper.class})
public interface MAuctionTeamMapper extends EntityMapper<MAuctionTeamDTO, MAuctionTeam> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "user.user.id", target = "userUserId")
    @Mapping(source = "user.user.login", target = "userName")
    @Mapping(source = "auction.id", target = "auctionId")
    @Mapping(source = "auction.name", target = "auctionName")
    MAuctionTeamDTO toDto(MAuctionTeam mAuctionTeam);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "userUserId", target = "user")
    @Mapping(source = "auctionId", target = "auction")
    MAuctionTeam toEntity(MAuctionTeamDTO mAuctionTeamDTO);

    default MAuctionTeam fromId(Long id) {
        if (id == null) {
            return null;
        }
        MAuctionTeam mAuctionTeam = new MAuctionTeam();
        mAuctionTeam.setId(id);
        return mAuctionTeam;
    }
}
