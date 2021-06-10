package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionSubmissionLogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionSubmissionLog.class);
        MAuctionSubmissionLog mAuctionSubmissionLog1 = new MAuctionSubmissionLog();
        mAuctionSubmissionLog1.setId(1L);
        MAuctionSubmissionLog mAuctionSubmissionLog2 = new MAuctionSubmissionLog();
        mAuctionSubmissionLog2.setId(mAuctionSubmissionLog1.getId());
        assertThat(mAuctionSubmissionLog1).isEqualTo(mAuctionSubmissionLog2);
        mAuctionSubmissionLog2.setId(2L);
        assertThat(mAuctionSubmissionLog1).isNotEqualTo(mAuctionSubmissionLog2);
        mAuctionSubmissionLog1.setId(null);
        assertThat(mAuctionSubmissionLog1).isNotEqualTo(mAuctionSubmissionLog2);
    }
}
