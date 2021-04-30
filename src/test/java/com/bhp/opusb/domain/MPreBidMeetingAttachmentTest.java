package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPreBidMeetingAttachmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPreBidMeetingAttachment.class);
        MPreBidMeetingAttachment mPreBidMeetingAttachment1 = new MPreBidMeetingAttachment();
        mPreBidMeetingAttachment1.setId(1L);
        MPreBidMeetingAttachment mPreBidMeetingAttachment2 = new MPreBidMeetingAttachment();
        mPreBidMeetingAttachment2.setId(mPreBidMeetingAttachment1.getId());
        assertThat(mPreBidMeetingAttachment1).isEqualTo(mPreBidMeetingAttachment2);
        mPreBidMeetingAttachment2.setId(2L);
        assertThat(mPreBidMeetingAttachment1).isNotEqualTo(mPreBidMeetingAttachment2);
        mPreBidMeetingAttachment1.setId(null);
        assertThat(mPreBidMeetingAttachment1).isNotEqualTo(mPreBidMeetingAttachment2);
    }
}
