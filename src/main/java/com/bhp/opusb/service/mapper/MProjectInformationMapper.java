package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MProjectInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MProjectInformation} and its DTO {@link MProjectInformationDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingMapper.class, ADOrganizationMapper.class, CAttachmentMapper.class})
public interface MProjectInformationMapper extends EntityMapper<MProjectInformationDTO, MProjectInformation> {

    @Mapping(source = "bidding.id", target = "biddingId")
    @Mapping(source = "bidding.name", target = "biddingName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "attachment.id", target = "attachmentId")
    MProjectInformationDTO toDto(MProjectInformation mProjectInformation);

    @Mapping(source = "biddingId", target = "bidding")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "attachmentId", target = "attachment")
    MProjectInformation toEntity(MProjectInformationDTO mProjectInformationDTO);

    default MProjectInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProjectInformation mProjectInformation = new MProjectInformation();
        mProjectInformation.setId(id);
        return mProjectInformation;
    }
}
