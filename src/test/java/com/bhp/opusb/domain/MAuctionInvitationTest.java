package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionInvitationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionInvitation.class);
        MAuctionInvitation mAuctionInvitation1 = new MAuctionInvitation();
        mAuctionInvitation1.setId(1L);
        MAuctionInvitation mAuctionInvitation2 = new MAuctionInvitation();
        mAuctionInvitation2.setId(mAuctionInvitation1.getId());
        assertThat(mAuctionInvitation1).isEqualTo(mAuctionInvitation2);
        mAuctionInvitation2.setId(2L);
        assertThat(mAuctionInvitation1).isNotEqualTo(mAuctionInvitation2);
        mAuctionInvitation1.setId(null);
        assertThat(mAuctionInvitation1).isNotEqualTo(mAuctionInvitation2);
    }
}
