package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvaluationTeamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvaluationTeam.class);
        MBiddingEvaluationTeam mBiddingEvaluationTeam1 = new MBiddingEvaluationTeam();
        mBiddingEvaluationTeam1.setId(1L);
        MBiddingEvaluationTeam mBiddingEvaluationTeam2 = new MBiddingEvaluationTeam();
        mBiddingEvaluationTeam2.setId(mBiddingEvaluationTeam1.getId());
        assertThat(mBiddingEvaluationTeam1).isEqualTo(mBiddingEvaluationTeam2);
        mBiddingEvaluationTeam2.setId(2L);
        assertThat(mBiddingEvaluationTeam1).isNotEqualTo(mBiddingEvaluationTeam2);
        mBiddingEvaluationTeam1.setId(null);
        assertThat(mBiddingEvaluationTeam1).isNotEqualTo(mBiddingEvaluationTeam2);
    }
}
