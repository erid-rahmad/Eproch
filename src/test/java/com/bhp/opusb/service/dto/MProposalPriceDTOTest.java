package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalPriceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalPriceDTO.class);
        MProposalPriceDTO mProposalPriceDTO1 = new MProposalPriceDTO();
        mProposalPriceDTO1.setId(1L);
        MProposalPriceDTO mProposalPriceDTO2 = new MProposalPriceDTO();
        assertThat(mProposalPriceDTO1).isNotEqualTo(mProposalPriceDTO2);
        mProposalPriceDTO2.setId(mProposalPriceDTO1.getId());
        assertThat(mProposalPriceDTO1).isEqualTo(mProposalPriceDTO2);
        mProposalPriceDTO2.setId(2L);
        assertThat(mProposalPriceDTO1).isNotEqualTo(mProposalPriceDTO2);
        mProposalPriceDTO1.setId(null);
        assertThat(mProposalPriceDTO1).isNotEqualTo(mProposalPriceDTO2);
    }
}
