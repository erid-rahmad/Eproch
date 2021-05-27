package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvalResultLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvalResultLine.class);
        MBiddingEvalResultLine mBiddingEvalResultLine1 = new MBiddingEvalResultLine();
        mBiddingEvalResultLine1.setId(1L);
        MBiddingEvalResultLine mBiddingEvalResultLine2 = new MBiddingEvalResultLine();
        mBiddingEvalResultLine2.setId(mBiddingEvalResultLine1.getId());
        assertThat(mBiddingEvalResultLine1).isEqualTo(mBiddingEvalResultLine2);
        mBiddingEvalResultLine2.setId(2L);
        assertThat(mBiddingEvalResultLine1).isNotEqualTo(mBiddingEvalResultLine2);
        mBiddingEvalResultLine1.setId(null);
        assertThat(mBiddingEvalResultLine1).isNotEqualTo(mBiddingEvalResultLine2);
    }
}
