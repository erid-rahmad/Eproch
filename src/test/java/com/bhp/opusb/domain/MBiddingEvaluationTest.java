package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvaluationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvaluation.class);
        MBiddingEvaluation mBiddingEvaluation1 = new MBiddingEvaluation();
        mBiddingEvaluation1.setId(1L);
        MBiddingEvaluation mBiddingEvaluation2 = new MBiddingEvaluation();
        mBiddingEvaluation2.setId(mBiddingEvaluation1.getId());
        assertThat(mBiddingEvaluation1).isEqualTo(mBiddingEvaluation2);
        mBiddingEvaluation2.setId(2L);
        assertThat(mBiddingEvaluation1).isNotEqualTo(mBiddingEvaluation2);
        mBiddingEvaluation1.setId(null);
        assertThat(mBiddingEvaluation1).isNotEqualTo(mBiddingEvaluation2);
    }
}
