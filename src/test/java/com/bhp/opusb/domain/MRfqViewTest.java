package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRfqViewTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRfqView.class);
        MRfqView mRfqView1 = new MRfqView();
        mRfqView1.setId(1L);
        MRfqView mRfqView2 = new MRfqView();
        mRfqView2.setId(mRfqView1.getId());
        assertThat(mRfqView1).isEqualTo(mRfqView2);
        mRfqView2.setId(2L);
        assertThat(mRfqView1).isNotEqualTo(mRfqView2);
        mRfqView1.setId(null);
        assertThat(mRfqView1).isNotEqualTo(mRfqView2);
    }
}
