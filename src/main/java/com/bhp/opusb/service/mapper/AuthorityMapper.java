package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.Authority;
import com.bhp.opusb.domain.ScAuthority;
import com.bhp.opusb.service.dto.ScAuthorityDTO;

import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ScAuthority} and its DTO {@link ScAuthorityDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthorityMapper {

    default Authority fromName(String name) {
        if (name == null) {
            return null;
        }
        Authority authority = new Authority();
        authority.setName(name);
        return authority;
    }
}
