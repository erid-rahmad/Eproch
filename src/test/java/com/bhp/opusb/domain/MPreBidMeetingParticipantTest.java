package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPreBidMeetingParticipantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPreBidMeetingParticipant.class);
        MPreBidMeetingParticipant mPreBidMeetingParticipant1 = new MPreBidMeetingParticipant();
        mPreBidMeetingParticipant1.setId(1L);
        MPreBidMeetingParticipant mPreBidMeetingParticipant2 = new MPreBidMeetingParticipant();
        mPreBidMeetingParticipant2.setId(mPreBidMeetingParticipant1.getId());
        assertThat(mPreBidMeetingParticipant1).isEqualTo(mPreBidMeetingParticipant2);
        mPreBidMeetingParticipant2.setId(2L);
        assertThat(mPreBidMeetingParticipant1).isNotEqualTo(mPreBidMeetingParticipant2);
        mPreBidMeetingParticipant1.setId(null);
        assertThat(mPreBidMeetingParticipant1).isNotEqualTo(mPreBidMeetingParticipant2);
    }
}
