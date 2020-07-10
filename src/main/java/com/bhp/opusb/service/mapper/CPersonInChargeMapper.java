package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CPersonInChargeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CPersonInCharge} and its DTO {@link CPersonInChargeDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CVendorMapper.class, ADOrganizationMapper.class})
public interface CPersonInChargeMapper extends EntityMapper<CPersonInChargeDTO, CPersonInCharge> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    CPersonInChargeDTO toDto(CPersonInCharge cPersonInCharge);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CPersonInCharge toEntity(CPersonInChargeDTO cPersonInChargeDTO);

    default CPersonInCharge fromId(Long id) {
        if (id == null) {
            return null;
        }
        CPersonInCharge cPersonInCharge = new CPersonInCharge();
        cPersonInCharge.setId(id);
        return cPersonInCharge;
    }
}
