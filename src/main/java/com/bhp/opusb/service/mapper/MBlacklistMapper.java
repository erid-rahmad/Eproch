package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBlacklistDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBlacklist} and its DTO {@link MBlacklistDTO}.
 */
@Mapper(componentModel = "spring", uses = {CAttachmentMapper.class, ADOrganizationMapper.class, CVendorMapper.class, CBusinessCategoryMapper.class})
public interface MBlacklistMapper extends EntityMapper<MBlacklistDTO, MBlacklist> {

    @Mapping(source = "attachment.id", target = "attachmentId")
    @Mapping(source = "attachment.fileName", target = "fileName")
    @Mapping(source = "attachment.downloadUrl", target = "downloadUrl")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.name", target = "vendorName")
    @Mapping(source = "businessCategory.id", target = "businessCategoryId")
    @Mapping(source = "subBusinessCategory.id", target = "subBusinessCategoryId")
    MBlacklistDTO toDto(MBlacklist mBlacklist);

    @Mapping(source = "attachmentId", target = "attachment")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "vendorId", target = "vendor")
    @Mapping(source = "businessCategoryId", target = "businessCategory")
    @Mapping(source = "subBusinessCategoryId", target = "subBusinessCategory")
    MBlacklist toEntity(MBlacklistDTO mBlacklistDTO);

    default MBlacklist fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBlacklist mBlacklist = new MBlacklist();
        mBlacklist.setId(id);
        return mBlacklist;
    }
}
