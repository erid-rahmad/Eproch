package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.CBankDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CBank} and its DTO {@link CBankDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface CBankMapper extends EntityMapper<CBankDTO, CBank> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    CBankDTO toDto(CBank cBank);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    CBank toEntity(CBankDTO cBankDTO);

    default CBank fromId(Long id) {
        if (id == null) {
            return null;
        }
        CBank cBank = new CBank();
        cBank.setId(id);
        return cBank;
    }
}
