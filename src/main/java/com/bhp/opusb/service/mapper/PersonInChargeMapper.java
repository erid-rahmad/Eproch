package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.PersonInChargeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonInCharge} and its DTO {@link PersonInChargeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, VendorMapper.class})
public interface PersonInChargeMapper extends EntityMapper<PersonInChargeDTO, PersonInCharge> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "vendor.id", target = "vendorId")
    PersonInChargeDTO toDto(PersonInCharge personInCharge);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "vendorId", target = "vendor")
    PersonInCharge toEntity(PersonInChargeDTO personInChargeDTO);

    default PersonInCharge fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonInCharge personInCharge = new PersonInCharge();
        personInCharge.setId(id);
        return personInCharge;
    }
}
