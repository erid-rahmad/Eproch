package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MComplaintTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MComplaint.class);
        MComplaint mComplaint1 = new MComplaint();
        mComplaint1.setId(1L);
        MComplaint mComplaint2 = new MComplaint();
        mComplaint2.setId(mComplaint1.getId());
        assertThat(mComplaint1).isEqualTo(mComplaint2);
        mComplaint2.setId(2L);
        assertThat(mComplaint1).isNotEqualTo(mComplaint2);
        mComplaint1.setId(null);
        assertThat(mComplaint1).isNotEqualTo(mComplaint2);
    }
}
