package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingNegotiationLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingNegotiationLine.class);
        MBiddingNegotiationLine mBiddingNegotiationLine1 = new MBiddingNegotiationLine();
        mBiddingNegotiationLine1.setId(1L);
        MBiddingNegotiationLine mBiddingNegotiationLine2 = new MBiddingNegotiationLine();
        mBiddingNegotiationLine2.setId(mBiddingNegotiationLine1.getId());
        assertThat(mBiddingNegotiationLine1).isEqualTo(mBiddingNegotiationLine2);
        mBiddingNegotiationLine2.setId(2L);
        assertThat(mBiddingNegotiationLine1).isNotEqualTo(mBiddingNegotiationLine2);
        mBiddingNegotiationLine1.setId(null);
        assertThat(mBiddingNegotiationLine1).isNotEqualTo(mBiddingNegotiationLine2);
    }
}
