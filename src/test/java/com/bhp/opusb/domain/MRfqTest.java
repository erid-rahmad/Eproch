package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRfqTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRfq.class);
        MRfq mRfq1 = new MRfq();
        mRfq1.setId(1L);
        MRfq mRfq2 = new MRfq();
        mRfq2.setId(mRfq1.getId());
        assertThat(mRfq1).isEqualTo(mRfq2);
        mRfq2.setId(2L);
        assertThat(mRfq1).isNotEqualTo(mRfq2);
        mRfq1.setId(null);
        assertThat(mRfq1).isNotEqualTo(mRfq2);
    }
}
