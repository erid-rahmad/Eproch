package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.service.dto.AdUserDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AdUser} and its DTO {@link AdUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CVendorMapper.class, ADOrganizationMapper.class})
public interface AdUserMapper extends EntityMapper<AdUserDTO, AdUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.password", target = "password")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "CVendor.id", target = "cVendorId")
    @Mapping(source = "CVendor.name", target = "cVendorName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    AdUserDTO toDto(AdUser adUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "cVendorId", target = "cVendor")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    AdUser toEntity(AdUserDTO adUserDTO);

    default AdUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdUser adUser = new AdUser();
        adUser.setId(id);
        return adUser;
    }
}
