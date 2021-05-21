package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MProposalPrice;
import com.bhp.opusb.service.dto.MProposalPriceDTO;
import com.bhp.opusb.service.dto.MProposalPriceVM;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MProposalPrice} and its DTO {@link MProposalPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {MBiddingSubmissionMapper.class, ADOrganizationMapper.class})
public interface MProposalPriceVMMapper extends EntityMapper<MProposalPriceVM, MProposalPrice> {

    @Mapping(source = "biddingSubmission.id", target = "biddingSubmissionId")
    @Mapping(source = "biddingSubmission.bidding.name", target = "biddingSubmissionName")
    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    MProposalPriceVM toDto(MProposalPrice mProposalPrice);

    @Mapping(source = "biddingSubmissionId", target = "biddingSubmission")
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    MProposalPrice toEntity(MProposalPriceVM mProposalPriceDTO);

    default MProposalPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProposalPrice mProposalPrice = new MProposalPrice();
        mProposalPrice.setId(id);
        return mProposalPrice;
    }
}
