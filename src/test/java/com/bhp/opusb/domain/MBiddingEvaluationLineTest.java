package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvaluationLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvaluationLine.class);
        MBiddingEvaluationLine mBiddingEvaluationLine1 = new MBiddingEvaluationLine();
        mBiddingEvaluationLine1.setId(1L);
        MBiddingEvaluationLine mBiddingEvaluationLine2 = new MBiddingEvaluationLine();
        mBiddingEvaluationLine2.setId(mBiddingEvaluationLine1.getId());
        assertThat(mBiddingEvaluationLine1).isEqualTo(mBiddingEvaluationLine2);
        mBiddingEvaluationLine2.setId(2L);
        assertThat(mBiddingEvaluationLine1).isNotEqualTo(mBiddingEvaluationLine2);
        mBiddingEvaluationLine1.setId(null);
        assertThat(mBiddingEvaluationLine1).isNotEqualTo(mBiddingEvaluationLine2);
    }
}
