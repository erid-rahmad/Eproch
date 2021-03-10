package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MSubmissionSubItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MSubmissionSubItem} and its DTO {@link MSubmissionSubItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingSubItemMapper.class, ADOrganizationMapper.class, MBiddingSubmissionLineMapper.class})
public interface MSubmissionSubItemMapper extends EntityMapper<MSubmissionSubItemDTO, MSubmissionSubItem> {

    @Mapping(source = "biddingSubItem.id", target = "biddingSubItemId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "MBiddingSubmissionLine.id", target = "MBiddingSubmissionLineId")
    MSubmissionSubItemDTO toDto(MSubmissionSubItem mSubmissionSubItem);

    @Mapping(source = "biddingSubItemId", target = "biddingSubItem")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "MBiddingSubmissionLineId", target = "MBiddingSubmissionLine")
    MSubmissionSubItem toEntity(MSubmissionSubItemDTO mSubmissionSubItemDTO);

    default MSubmissionSubItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        MSubmissionSubItem mSubmissionSubItem = new MSubmissionSubItem();
        mSubmissionSubItem.setId(id);
        return mSubmissionSubItem;
    }
}
