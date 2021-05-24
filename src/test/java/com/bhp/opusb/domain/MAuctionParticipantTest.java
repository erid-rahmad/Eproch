package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionParticipantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionParticipant.class);
        MAuctionParticipant mAuctionParticipant1 = new MAuctionParticipant();
        mAuctionParticipant1.setId(1L);
        MAuctionParticipant mAuctionParticipant2 = new MAuctionParticipant();
        mAuctionParticipant2.setId(mAuctionParticipant1.getId());
        assertThat(mAuctionParticipant1).isEqualTo(mAuctionParticipant2);
        mAuctionParticipant2.setId(2L);
        assertThat(mAuctionParticipant1).isNotEqualTo(mAuctionParticipant2);
        mAuctionParticipant1.setId(null);
        assertThat(mAuctionParticipant1).isNotEqualTo(mAuctionParticipant2);
    }
}
