package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvalTeamLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvalTeamLine.class);
        MBiddingEvalTeamLine mBiddingEvalTeamLine1 = new MBiddingEvalTeamLine();
        mBiddingEvalTeamLine1.setId(1L);
        MBiddingEvalTeamLine mBiddingEvalTeamLine2 = new MBiddingEvalTeamLine();
        mBiddingEvalTeamLine2.setId(mBiddingEvalTeamLine1.getId());
        assertThat(mBiddingEvalTeamLine1).isEqualTo(mBiddingEvalTeamLine2);
        mBiddingEvalTeamLine2.setId(2L);
        assertThat(mBiddingEvalTeamLine1).isNotEqualTo(mBiddingEvalTeamLine2);
        mBiddingEvalTeamLine1.setId(null);
        assertThat(mBiddingEvalTeamLine1).isNotEqualTo(mBiddingEvalTeamLine2);
    }
}
