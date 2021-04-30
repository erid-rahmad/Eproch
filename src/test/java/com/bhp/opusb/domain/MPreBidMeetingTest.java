package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPreBidMeetingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPreBidMeeting.class);
        MPreBidMeeting mPreBidMeeting1 = new MPreBidMeeting();
        mPreBidMeeting1.setId(1L);
        MPreBidMeeting mPreBidMeeting2 = new MPreBidMeeting();
        mPreBidMeeting2.setId(mPreBidMeeting1.getId());
        assertThat(mPreBidMeeting1).isEqualTo(mPreBidMeeting2);
        mPreBidMeeting2.setId(2L);
        assertThat(mPreBidMeeting1).isNotEqualTo(mPreBidMeeting2);
        mPreBidMeeting1.setId(null);
        assertThat(mPreBidMeeting1).isNotEqualTo(mPreBidMeeting2);
    }
}
