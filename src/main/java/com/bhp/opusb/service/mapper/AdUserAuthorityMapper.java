package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.AdUserAuthority;
import com.bhp.opusb.service.dto.AdUserAuthorityDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AdUserAuthority} and its DTO {@link AdUserAuthorityDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, AdUserMapper.class, ScAuthorityMapper.class})
public interface AdUserAuthorityMapper extends EntityMapper<AdUserAuthorityDTO, AdUserAuthority> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "user.user.id", target = "userId")
    @Mapping(source = "user.user.login", target = "userName")
    @Mapping(source = "authority.id", target = "authorityId")
    @Mapping(source = "authority.authority.name", target = "authorityName")
    AdUserAuthorityDTO toDto(AdUserAuthority adUserAuthority);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "authorityId", target = "authority")
    AdUserAuthority toEntity(AdUserAuthorityDTO adUserAuthorityDTO);

    default AdUserAuthority fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdUserAuthority adUserAuthority = new AdUserAuthority();
        adUserAuthority.setId(id);
        return adUserAuthority;
    }
}
