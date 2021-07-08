package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MWarningLetterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MWarningLetter} and its DTO {@link MWarningLetterDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, CVendorMapper.class})
public interface MWarningLetterMapper extends EntityMapper<MWarningLetterDTO, MWarningLetter> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    MWarningLetterDTO toDto(MWarningLetter mWarningLetter);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "vendorId", target = "vendor")
    MWarningLetter toEntity(MWarningLetterDTO mWarningLetterDTO);

    default MWarningLetter fromId(Long id) {
        if (id == null) {
            return null;
        }
        MWarningLetter mWarningLetter = new MWarningLetter();
        mWarningLetter.setId(id);
        return mWarningLetter;
    }
}
