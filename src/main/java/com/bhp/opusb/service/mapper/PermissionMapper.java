package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.PermissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Permission} and its DTO {@link PermissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {



    default Permission fromId(Long id) {
        if (id == null) {
            return null;
        }
        Permission permission = new Permission();
        permission.setId(id);
        return permission;
    }
}
