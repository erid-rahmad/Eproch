package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CAnnouncementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAnnouncement.class);
        CAnnouncement cAnnouncement1 = new CAnnouncement();
        cAnnouncement1.setId(1L);
        CAnnouncement cAnnouncement2 = new CAnnouncement();
        cAnnouncement2.setId(cAnnouncement1.getId());
        assertThat(cAnnouncement1).isEqualTo(cAnnouncement2);
        cAnnouncement2.setId(2L);
        assertThat(cAnnouncement1).isNotEqualTo(cAnnouncement2);
        cAnnouncement1.setId(null);
        assertThat(cAnnouncement1).isNotEqualTo(cAnnouncement2);
    }
}
