package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRfqLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRfqLine.class);
        MRfqLine mRfqLine1 = new MRfqLine();
        mRfqLine1.setId(1L);
        MRfqLine mRfqLine2 = new MRfqLine();
        mRfqLine2.setId(mRfqLine1.getId());
        assertThat(mRfqLine1).isEqualTo(mRfqLine2);
        mRfqLine2.setId(2L);
        assertThat(mRfqLine1).isNotEqualTo(mRfqLine2);
        mRfqLine1.setId(null);
        assertThat(mRfqLine1).isNotEqualTo(mRfqLine2);
    }
}
