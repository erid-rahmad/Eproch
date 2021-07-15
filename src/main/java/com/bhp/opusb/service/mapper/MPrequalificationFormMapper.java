package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MPrequalificationFormDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MPrequalificationInformation} and its DTO {@link MPrequalificationInformationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, ADOrganizationMapper.class})
public interface MPrequalificationFormMapper extends EntityMapper<MPrequalificationFormDTO, MPrequalificationInformation> {

    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.fileName", target = "fileName")
    @Mapping(source = "attachment.downloadUrl", target = "downloadUrl")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    MPrequalificationFormDTO toDto(MPrequalificationInformation mPrequalificationInformation);

    @Mapping(source = "attachmentId", target = "attachment")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    MPrequalificationInformation toEntity(MPrequalificationFormDTO mPrequalificationInformationDTO);

    default MPrequalificationInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MPrequalificationInformation mPrequalificationInformation = new MPrequalificationInformation();
        mPrequalificationInformation.setId(id);
        return mPrequalificationInformation;
    }
}
