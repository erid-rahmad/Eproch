package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvalTeamLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvalTeamLineDTO.class);
        MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO1 = new MBiddingEvalTeamLineDTO();
        mBiddingEvalTeamLineDTO1.setId(1L);
        MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO2 = new MBiddingEvalTeamLineDTO();
        assertThat(mBiddingEvalTeamLineDTO1).isNotEqualTo(mBiddingEvalTeamLineDTO2);
        mBiddingEvalTeamLineDTO2.setId(mBiddingEvalTeamLineDTO1.getId());
        assertThat(mBiddingEvalTeamLineDTO1).isEqualTo(mBiddingEvalTeamLineDTO2);
        mBiddingEvalTeamLineDTO2.setId(2L);
        assertThat(mBiddingEvalTeamLineDTO1).isNotEqualTo(mBiddingEvalTeamLineDTO2);
        mBiddingEvalTeamLineDTO1.setId(null);
        assertThat(mBiddingEvalTeamLineDTO1).isNotEqualTo(mBiddingEvalTeamLineDTO2);
    }
}
