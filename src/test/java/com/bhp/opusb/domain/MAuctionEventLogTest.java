package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionEventLogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionEventLog.class);
        MAuctionEventLog mAuctionEventLog1 = new MAuctionEventLog();
        mAuctionEventLog1.setId(1L);
        MAuctionEventLog mAuctionEventLog2 = new MAuctionEventLog();
        mAuctionEventLog2.setId(mAuctionEventLog1.getId());
        assertThat(mAuctionEventLog1).isEqualTo(mAuctionEventLog2);
        mAuctionEventLog2.setId(2L);
        assertThat(mAuctionEventLog1).isNotEqualTo(mAuctionEventLog2);
        mAuctionEventLog1.setId(null);
        assertThat(mAuctionEventLog1).isNotEqualTo(mAuctionEventLog2);
    }
}
