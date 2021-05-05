package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalPriceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalPrice.class);
        MProposalPrice mProposalPrice1 = new MProposalPrice();
        mProposalPrice1.setId(1L);
        MProposalPrice mProposalPrice2 = new MProposalPrice();
        mProposalPrice2.setId(mProposalPrice1.getId());
        assertThat(mProposalPrice1).isEqualTo(mProposalPrice2);
        mProposalPrice2.setId(2L);
        assertThat(mProposalPrice1).isNotEqualTo(mProposalPrice2);
        mProposalPrice1.setId(null);
        assertThat(mProposalPrice1).isNotEqualTo(mProposalPrice2);
    }
}
