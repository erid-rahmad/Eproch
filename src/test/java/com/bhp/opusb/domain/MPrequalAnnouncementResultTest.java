package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalAnnouncementResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalAnnouncementResult.class);
        MPrequalAnnouncementResult mPrequalAnnouncementResult1 = new MPrequalAnnouncementResult();
        mPrequalAnnouncementResult1.setId(1L);
        MPrequalAnnouncementResult mPrequalAnnouncementResult2 = new MPrequalAnnouncementResult();
        mPrequalAnnouncementResult2.setId(mPrequalAnnouncementResult1.getId());
        assertThat(mPrequalAnnouncementResult1).isEqualTo(mPrequalAnnouncementResult2);
        mPrequalAnnouncementResult2.setId(2L);
        assertThat(mPrequalAnnouncementResult1).isNotEqualTo(mPrequalAnnouncementResult2);
        mPrequalAnnouncementResult1.setId(null);
        assertThat(mPrequalAnnouncementResult1).isNotEqualTo(mPrequalAnnouncementResult2);
    }
}
