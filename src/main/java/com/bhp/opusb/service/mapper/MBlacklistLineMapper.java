package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBlacklistLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBlacklistLine} and its DTO {@link MBlacklistLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBlacklistMapper.class, ADOrganizationMapper.class, AdUserMapper.class, CFunctionaryMapper.class})
public interface MBlacklistLineMapper extends EntityMapper<MBlacklistLineDTO, MBlacklistLine> {

    @Mapping(source = "blacklist.id", target = "blacklistId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "pic.id", target = "picId")
    @Mapping(source = "pic.user.firstName", target = "picFirstName")
    @Mapping(source = "pic.user.lastName", target = "picLastName")
    @Mapping(source = "functionary.id", target = "functionaryId")
    @Mapping(source = "functionary.name", target = "functionaryName")
    MBlacklistLineDTO toDto(MBlacklistLine mBlacklistLine);

    @Mapping(source = "blacklistId", target = "blacklist")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "picId", target = "pic")
    @Mapping(source = "functionaryId", target = "functionary")
    MBlacklistLine toEntity(MBlacklistLineDTO mBlacklistLineDTO);

    default MBlacklistLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBlacklistLine mBlacklistLine = new MBlacklistLine();
        mBlacklistLine.setId(id);
        return mBlacklistLine;
    }
}
