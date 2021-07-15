package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalificationInvitationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalificationInvitation} and its DTO {@link MPrequalificationInvitationDTO}.
 */
@Mapper(componentModel = "spring", uses = {MPrequalificationInformationMapper.class, ADOrganizationMapper.class, CBusinessCategoryMapper.class})
public interface MPrequalificationInvitationMapper extends EntityMapper<MPrequalificationInvitationDTO, MPrequalificationInvitation> {

    @Mapping(source = "prequalification.id", target = "prequalificationId")
    @Mapping(source = "prequalification.name", target = "prequalificationName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "businessCategory.name", target = "businessCategoryName")
    @Mapping(source = "businessSubCategory.id", target = "businessSubCategoryId")
    @Mapping(source = "businessSubCategory.name", target = "businessSubCategoryName")
    MPrequalificationInvitationDTO toDto(MPrequalificationInvitation mPrequalificationInvitation);

    @Mapping(source = "prequalificationId", target = "prequalification")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    @Mapping(source = "businessSubCategoryId", target = "businessSubCategory")
    MPrequalificationInvitation toEntity(MPrequalificationInvitationDTO mPrequalificationInvitationDTO);

    default MPrequalificationInvitation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalificationInvitation mPrequalificationInvitation = new MPrequalificationInvitation();
        mPrequalificationInvitation.setId(id);
        return mPrequalificationInvitation;
    }
}
