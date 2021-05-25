package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionTeamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionTeam.class);
        MAuctionTeam mAuctionTeam1 = new MAuctionTeam();
        mAuctionTeam1.setId(1L);
        MAuctionTeam mAuctionTeam2 = new MAuctionTeam();
        mAuctionTeam2.setId(mAuctionTeam1.getId());
        assertThat(mAuctionTeam1).isEqualTo(mAuctionTeam2);
        mAuctionTeam2.setId(2L);
        assertThat(mAuctionTeam1).isNotEqualTo(mAuctionTeam2);
        mAuctionTeam1.setId(null);
        assertThat(mAuctionTeam1).isNotEqualTo(mAuctionTeam2);
    }
}
