package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalPriceLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalPriceLine.class);
        MProposalPriceLine mProposalPriceLine1 = new MProposalPriceLine();
        mProposalPriceLine1.setId(1L);
        MProposalPriceLine mProposalPriceLine2 = new MProposalPriceLine();
        mProposalPriceLine2.setId(mProposalPriceLine1.getId());
        assertThat(mProposalPriceLine1).isEqualTo(mProposalPriceLine2);
        mProposalPriceLine2.setId(2L);
        assertThat(mProposalPriceLine1).isNotEqualTo(mProposalPriceLine2);
        mProposalPriceLine1.setId(null);
        assertThat(mProposalPriceLine1).isNotEqualTo(mProposalPriceLine2);
    }
}
