package com.bhp.opusb.service.mapper;


import com.bhp.opusb.domain.MProposalPriceSubItem;
import com.bhp.opusb.service.dto.MProposalPriceSubItemDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link MProposalPriceSubItem} and its DTO {@link MProposalPriceSubItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {ADOrganizationMapper.class, MBiddingSubItemLineMapper.class, MProposalPriceLineMapper.class})
public interface MProposalPriceSubItemMapper extends EntityMapper<MProposalPriceSubItemDTO, MProposalPriceSubItem> {

    @Mapping(source = "adOrganization.id", target = "adOrganizationId")
    @Mapping(source = "adOrganization.name", target = "adOrganizationName")
    @Mapping(source = "biddingSubItemLine.id", target = "biddingSubItemLineId")
    @Mapping(source = "biddingSubItemLine.product.name", target = "biddingSubItemLineName")
    @Mapping(source = "proposalPriceLine.id", target = "proposalPriceLineId")
    @Mapping(source = "proposalPriceLine.biddingLine.product.name", target = "proposalPriceLineName")
    MProposalPriceSubItemDTO toDto(MProposalPriceSubItem mProposalPriceSubItem);

    @Mapping(source = "adOrganizationId", target = "adOrganization")
    @Mapping(source = "biddingSubItemLineId", target = "biddingSubItemLine")
    @Mapping(source = "proposalPriceLineId", target = "proposalPriceLine")
    MProposalPriceSubItem toEntity(MProposalPriceSubItemDTO mProposalPriceSubItemDTO);

    default MProposalPriceSubItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        MProposalPriceSubItem mProposalPriceSubItem = new MProposalPriceSubItem();
        mProposalPriceSubItem.setId(id);
        return mProposalPriceSubItem;
    }
}
