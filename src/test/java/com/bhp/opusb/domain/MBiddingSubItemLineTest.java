package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingSubItemLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingSubItemLine.class);
        MBiddingSubItemLine mBiddingSubItemLine1 = new MBiddingSubItemLine();
        mBiddingSubItemLine1.setId(1L);
        MBiddingSubItemLine mBiddingSubItemLine2 = new MBiddingSubItemLine();
        mBiddingSubItemLine2.setId(mBiddingSubItemLine1.getId());
        assertThat(mBiddingSubItemLine1).isEqualTo(mBiddingSubItemLine2);
        mBiddingSubItemLine2.setId(2L);
        assertThat(mBiddingSubItemLine1).isNotEqualTo(mBiddingSubItemLine2);
        mBiddingSubItemLine1.setId(null);
        assertThat(mBiddingSubItemLine1).isNotEqualTo(mBiddingSubItemLine2);
    }
}
