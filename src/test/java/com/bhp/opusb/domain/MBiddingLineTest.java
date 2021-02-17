package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingLine.class);
        MBiddingLine mBiddingLine1 = new MBiddingLine();
        mBiddingLine1.setId(1L);
        MBiddingLine mBiddingLine2 = new MBiddingLine();
        mBiddingLine2.setId(mBiddingLine1.getId());
        assertThat(mBiddingLine1).isEqualTo(mBiddingLine2);
        mBiddingLine2.setId(2L);
        assertThat(mBiddingLine1).isNotEqualTo(mBiddingLine2);
        mBiddingLine1.setId(null);
        assertThat(mBiddingLine1).isNotEqualTo(mBiddingLine2);
    }
}
