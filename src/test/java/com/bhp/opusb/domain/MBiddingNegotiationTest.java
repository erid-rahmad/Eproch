package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingNegotiationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingNegotiation.class);
        MBiddingNegotiation mBiddingNegotiation1 = new MBiddingNegotiation();
        mBiddingNegotiation1.setId(1L);
        MBiddingNegotiation mBiddingNegotiation2 = new MBiddingNegotiation();
        mBiddingNegotiation2.setId(mBiddingNegotiation1.getId());
        assertThat(mBiddingNegotiation1).isEqualTo(mBiddingNegotiation2);
        mBiddingNegotiation2.setId(2L);
        assertThat(mBiddingNegotiation1).isNotEqualTo(mBiddingNegotiation2);
        mBiddingNegotiation1.setId(null);
        assertThat(mBiddingNegotiation1).isNotEqualTo(mBiddingNegotiation2);
    }
}
