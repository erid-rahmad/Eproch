package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.ScAuthority;
import com.bhp.opusb.service.dto.ScAuthorityDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ScAuthority} and its DTO {@link ScAuthorityDTO}.
 */
@Mapper(componentModel = "spring", uses = {AuthorityMapper.class, ADOrganizationMapper.class})
public interface ScAuthorityMapper extends EntityMapper<ScAuthorityDTO, ScAuthority> {

    @Mapping(source = "authority.name", target = "authorityName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    ScAuthorityDTO toDto(ScAuthority scAuthority);

    @Mapping(source = "authorityName", target = "authority")
    @Mapping(target = "scAccesses", ignore = true)
    @Mapping(target = "removeScAccess", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    ScAuthority toEntity(ScAuthorityDTO scAuthorityDTO);

    default ScAuthority fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScAuthority scAuthority = new ScAuthority();
        scAuthority.setId(id);
        return scAuthority;
    }
}
