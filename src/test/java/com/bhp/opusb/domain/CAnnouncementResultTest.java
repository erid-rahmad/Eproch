package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CAnnouncementResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAnnouncementResult.class);
        CAnnouncementResult cAnnouncementResult1 = new CAnnouncementResult();
        cAnnouncementResult1.setId(1L);
        CAnnouncementResult cAnnouncementResult2 = new CAnnouncementResult();
        cAnnouncementResult2.setId(cAnnouncementResult1.getId());
        assertThat(cAnnouncementResult1).isEqualTo(cAnnouncementResult2);
        cAnnouncementResult2.setId(2L);
        assertThat(cAnnouncementResult1).isNotEqualTo(cAnnouncementResult2);
        cAnnouncementResult1.setId(null);
        assertThat(cAnnouncementResult1).isNotEqualTo(cAnnouncementResult2);
    }
}
