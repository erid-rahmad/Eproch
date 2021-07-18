package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvaluationTeamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvaluationTeamDTO.class);
        MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO1 = new MBiddingEvaluationTeamDTO();
        mBiddingEvaluationTeamDTO1.setId(1L);
        MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO2 = new MBiddingEvaluationTeamDTO();
        assertThat(mBiddingEvaluationTeamDTO1).isNotEqualTo(mBiddingEvaluationTeamDTO2);
        mBiddingEvaluationTeamDTO2.setId(mBiddingEvaluationTeamDTO1.getId());
        assertThat(mBiddingEvaluationTeamDTO1).isEqualTo(mBiddingEvaluationTeamDTO2);
        mBiddingEvaluationTeamDTO2.setId(2L);
        assertThat(mBiddingEvaluationTeamDTO1).isNotEqualTo(mBiddingEvaluationTeamDTO2);
        mBiddingEvaluationTeamDTO1.setId(null);
        assertThat(mBiddingEvaluationTeamDTO1).isNotEqualTo(mBiddingEvaluationTeamDTO2);
    }
}
