package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingNegotiationChatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingNegotiationChat.class);
        MBiddingNegotiationChat mBiddingNegotiationChat1 = new MBiddingNegotiationChat();
        mBiddingNegotiationChat1.setId(1L);
        MBiddingNegotiationChat mBiddingNegotiationChat2 = new MBiddingNegotiationChat();
        mBiddingNegotiationChat2.setId(mBiddingNegotiationChat1.getId());
        assertThat(mBiddingNegotiationChat1).isEqualTo(mBiddingNegotiationChat2);
        mBiddingNegotiationChat2.setId(2L);
        assertThat(mBiddingNegotiationChat1).isNotEqualTo(mBiddingNegotiationChat2);
        mBiddingNegotiationChat1.setId(null);
        assertThat(mBiddingNegotiationChat1).isNotEqualTo(mBiddingNegotiationChat2);
    }
}
