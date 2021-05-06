package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.*;
import com.bhp.opusb.service.dto.MProposalPriceLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MProposalPriceLine} and its DTO {@link MProposalPriceLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MProposalPriceMapper.class, MBiddingLineMapper.class})
public interface MProposalPriceLineMapper extends EntityMapper<MProposalPriceLineDTO, MProposalPriceLine> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "proposalPrice.id", target = "proposalPriceId")
    @Mapping(source = "proposalPrice.biddingSubmission.bidding.name", target = "proposalPriceName")
    @Mapping(source = "biddingLine.id", target = "biddingLineId")
    @Mapping(source = "biddingLine.product.name", target = "biddingLineName")
    MProposalPriceLineDTO toDto(MProposalPriceLine mProposalPriceLine);

    @Mapping(target = "mProposalPriceSubItems", ignore = true)
    @Mapping(target = "removeMProposalPriceSubItem", ignore = true)
    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "proposalPriceId", target = "proposalPrice")
    @Mapping(source = "biddingLineId", target = "biddingLine")
    MProposalPriceLine toEntity(MProposalPriceLineDTO mProposalPriceLineDTO);

    default MProposalPriceLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProposalPriceLine mProposalPriceLine = new MProposalPriceLine();
        mProposalPriceLine.setId(id);
        return mProposalPriceLine;
    }
}
