package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MBiddingSubmissionLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MBiddingSubmissionLine} and its DTO {@link MBiddingSubmissionLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingLineMapper.class, ADOrganizationMapper.class, MBiddingSubmissionMapper.class})
public interface MBiddingSubmissionLineMapper extends EntityMapper<MBiddingSubmissionLineDTO, MBiddingSubmissionLine> {

    @Mapping(source = "biddingLine.id", target = "biddingLineId")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "MBiddingSubmission.id", target = "MBiddingSubmissionId")
    MBiddingSubmissionLineDTO toDto(MBiddingSubmissionLine mBiddingSubmissionLine);

    @Mapping(target = "mSubmissionSubItems", ignore = true)
    @Mapping(target = "removeMSubmissionSubItem", ignore = true)
    @Mapping(source = "biddingLineId", target = "biddingLine")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "MBiddingSubmissionId", target = "MBiddingSubmission")
    MBiddingSubmissionLine toEntity(MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO);

    default MBiddingSubmissionLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MBiddingSubmissionLine mBiddingSubmissionLine = new MBiddingSubmissionLine();
        mBiddingSubmissionLine.setId(id);
        return mBiddingSubmissionLine;
    }
}
