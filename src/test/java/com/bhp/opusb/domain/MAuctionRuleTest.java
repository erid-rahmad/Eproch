package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionRuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionRule.class);
        MAuctionRule mAuctionRule1 = new MAuctionRule();
        mAuctionRule1.setId(1L);
        MAuctionRule mAuctionRule2 = new MAuctionRule();
        mAuctionRule2.setId(mAuctionRule1.getId());
        assertThat(mAuctionRule1).isEqualTo(mAuctionRule2);
        mAuctionRule2.setId(2L);
        assertThat(mAuctionRule1).isNotEqualTo(mAuctionRule2);
        mAuctionRule1.setId(null);
        assertThat(mAuctionRule1).isNotEqualTo(mAuctionRule2);
    }
}
