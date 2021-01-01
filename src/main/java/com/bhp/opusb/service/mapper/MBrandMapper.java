package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MBrand;
import com.bhp.opusb.service.dto.MBrandDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MBrand} and its DTO {@link MBrandDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class})
public interface MBrandMapper extends EntityMapper<MBrandDTO, MBrand> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    MBrandDTO toDto(MBrand mBrand);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    MBrand toEntity(MBrandDTO mBrandDTO);

    default MBrand fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBrand mBrand = new MBrand();
        mBrand.setId(id);
        return mBrand;
    }
}
