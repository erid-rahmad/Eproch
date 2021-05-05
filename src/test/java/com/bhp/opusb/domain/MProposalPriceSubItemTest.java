package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalPriceSubItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalPriceSubItem.class);
        MProposalPriceSubItem mProposalPriceSubItem1 = new MProposalPriceSubItem();
        mProposalPriceSubItem1.setId(1L);
        MProposalPriceSubItem mProposalPriceSubItem2 = new MProposalPriceSubItem();
        mProposalPriceSubItem2.setId(mProposalPriceSubItem1.getId());
        assertThat(mProposalPriceSubItem1).isEqualTo(mProposalPriceSubItem2);
        mProposalPriceSubItem2.setId(2L);
        assertThat(mProposalPriceSubItem1).isNotEqualTo(mProposalPriceSubItem2);
        mProposalPriceSubItem1.setId(null);
        assertThat(mProposalPriceSubItem1).isNotEqualTo(mProposalPriceSubItem2);
    }
}
