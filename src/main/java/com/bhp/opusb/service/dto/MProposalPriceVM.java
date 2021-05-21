package com.bhp.opusb.service.dto;

import java.util.List;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MProposalPrice} entity.
 */
public class MProposalPriceVM extends MProposalPriceDTO {
    
    private List<MProposalPriceLineDTO> proposalPriceLines;

    public List<MProposalPriceLineDTO> getProposalPriceLines() {
        return proposalPriceLines;
    }

    public void setProposalPriceLines(List<MProposalPriceLineDTO> proposalPriceLines) {
        this.proposalPriceLines = proposalPriceLines;
    }
}
