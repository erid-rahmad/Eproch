package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingScheduleAttachmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingScheduleAttachment.class);
        MBiddingScheduleAttachment mBiddingScheduleAttachment1 = new MBiddingScheduleAttachment();
        mBiddingScheduleAttachment1.setId(1L);
        MBiddingScheduleAttachment mBiddingScheduleAttachment2 = new MBiddingScheduleAttachment();
        mBiddingScheduleAttachment2.setId(mBiddingScheduleAttachment1.getId());
        assertThat(mBiddingScheduleAttachment1).isEqualTo(mBiddingScheduleAttachment2);
        mBiddingScheduleAttachment2.setId(2L);
        assertThat(mBiddingScheduleAttachment1).isNotEqualTo(mBiddingScheduleAttachment2);
        mBiddingScheduleAttachment1.setId(null);
        assertThat(mBiddingScheduleAttachment1).isNotEqualTo(mBiddingScheduleAttachment2);
    }
}
