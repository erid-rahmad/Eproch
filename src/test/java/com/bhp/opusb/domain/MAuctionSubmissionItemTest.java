package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionSubmissionItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionSubmissionItem.class);
        MAuctionSubmissionItem mAuctionSubmissionItem1 = new MAuctionSubmissionItem();
        mAuctionSubmissionItem1.setId(1L);
        MAuctionSubmissionItem mAuctionSubmissionItem2 = new MAuctionSubmissionItem();
        mAuctionSubmissionItem2.setId(mAuctionSubmissionItem1.getId());
        assertThat(mAuctionSubmissionItem1).isEqualTo(mAuctionSubmissionItem2);
        mAuctionSubmissionItem2.setId(2L);
        assertThat(mAuctionSubmissionItem1).isNotEqualTo(mAuctionSubmissionItem2);
        mAuctionSubmissionItem1.setId(null);
        assertThat(mAuctionSubmissionItem1).isNotEqualTo(mAuctionSubmissionItem2);
    }
}
