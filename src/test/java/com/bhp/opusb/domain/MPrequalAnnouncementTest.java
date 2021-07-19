package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalAnnouncementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalAnnouncement.class);
        MPrequalAnnouncement mPrequalAnnouncement1 = new MPrequalAnnouncement();
        mPrequalAnnouncement1.setId(1L);
        MPrequalAnnouncement mPrequalAnnouncement2 = new MPrequalAnnouncement();
        mPrequalAnnouncement2.setId(mPrequalAnnouncement1.getId());
        assertThat(mPrequalAnnouncement1).isEqualTo(mPrequalAnnouncement2);
        mPrequalAnnouncement2.setId(2L);
        assertThat(mPrequalAnnouncement1).isNotEqualTo(mPrequalAnnouncement2);
        mPrequalAnnouncement1.setId(null);
        assertThat(mPrequalAnnouncement1).isNotEqualTo(mPrequalAnnouncement2);
    }
}
