package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingResult.class);
        MBiddingResult mBiddingResult1 = new MBiddingResult();
        mBiddingResult1.setId(1L);
        MBiddingResult mBiddingResult2 = new MBiddingResult();
        mBiddingResult2.setId(mBiddingResult1.getId());
        assertThat(mBiddingResult1).isEqualTo(mBiddingResult2);
        mBiddingResult2.setId(2L);
        assertThat(mBiddingResult1).isNotEqualTo(mBiddingResult2);
        mBiddingResult1.setId(null);
        assertThat(mBiddingResult1).isNotEqualTo(mBiddingResult2);
    }
}
