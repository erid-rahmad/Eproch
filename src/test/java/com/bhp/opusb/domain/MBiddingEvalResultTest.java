package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvalResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvalResult.class);
        MBiddingEvalResult mBiddingEvalResult1 = new MBiddingEvalResult();
        mBiddingEvalResult1.setId(1L);
        MBiddingEvalResult mBiddingEvalResult2 = new MBiddingEvalResult();
        mBiddingEvalResult2.setId(mBiddingEvalResult1.getId());
        assertThat(mBiddingEvalResult1).isEqualTo(mBiddingEvalResult2);
        mBiddingEvalResult2.setId(2L);
        assertThat(mBiddingEvalResult1).isNotEqualTo(mBiddingEvalResult2);
        mBiddingEvalResult1.setId(null);
        assertThat(mBiddingEvalResult1).isNotEqualTo(mBiddingEvalResult2);
    }
}
