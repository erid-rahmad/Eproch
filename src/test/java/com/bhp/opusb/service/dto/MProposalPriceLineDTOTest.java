package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalPriceLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalPriceLineDTO.class);
        MProposalPriceLineDTO mProposalPriceLineDTO1 = new MProposalPriceLineDTO();
        mProposalPriceLineDTO1.setId(1L);
        MProposalPriceLineDTO mProposalPriceLineDTO2 = new MProposalPriceLineDTO();
        assertThat(mProposalPriceLineDTO1).isNotEqualTo(mProposalPriceLineDTO2);
        mProposalPriceLineDTO2.setId(mProposalPriceLineDTO1.getId());
        assertThat(mProposalPriceLineDTO1).isEqualTo(mProposalPriceLineDTO2);
        mProposalPriceLineDTO2.setId(2L);
        assertThat(mProposalPriceLineDTO1).isNotEqualTo(mProposalPriceLineDTO2);
        mProposalPriceLineDTO1.setId(null);
        assertThat(mProposalPriceLineDTO1).isNotEqualTo(mProposalPriceLineDTO2);
    }
}
