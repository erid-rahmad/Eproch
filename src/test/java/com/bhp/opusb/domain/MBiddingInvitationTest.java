package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingInvitationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingInvitation.class);
        MBiddingInvitation mBiddingInvitation1 = new MBiddingInvitation();
        mBiddingInvitation1.setId(1L);
        MBiddingInvitation mBiddingInvitation2 = new MBiddingInvitation();
        mBiddingInvitation2.setId(mBiddingInvitation1.getId());
        assertThat(mBiddingInvitation1).isEqualTo(mBiddingInvitation2);
        mBiddingInvitation2.setId(2L);
        assertThat(mBiddingInvitation1).isNotEqualTo(mBiddingInvitation2);
        mBiddingInvitation1.setId(null);
        assertThat(mBiddingInvitation1).isNotEqualTo(mBiddingInvitation2);
    }
}
