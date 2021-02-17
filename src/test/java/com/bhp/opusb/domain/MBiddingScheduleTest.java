package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingScheduleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingSchedule.class);
        MBiddingSchedule mBiddingSchedule1 = new MBiddingSchedule();
        mBiddingSchedule1.setId(1L);
        MBiddingSchedule mBiddingSchedule2 = new MBiddingSchedule();
        mBiddingSchedule2.setId(mBiddingSchedule1.getId());
        assertThat(mBiddingSchedule1).isEqualTo(mBiddingSchedule2);
        mBiddingSchedule2.setId(2L);
        assertThat(mBiddingSchedule1).isNotEqualTo(mBiddingSchedule2);
        mBiddingSchedule1.setId(null);
        assertThat(mBiddingSchedule1).isNotEqualTo(mBiddingSchedule2);
    }
}
