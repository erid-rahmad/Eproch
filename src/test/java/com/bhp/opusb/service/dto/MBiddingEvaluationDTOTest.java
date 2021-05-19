package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvaluationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvaluationDTO.class);
        MBiddingEvaluationDTO mBiddingEvaluationDTO1 = new MBiddingEvaluationDTO();
        mBiddingEvaluationDTO1.setId(1L);
        MBiddingEvaluationDTO mBiddingEvaluationDTO2 = new MBiddingEvaluationDTO();
        assertThat(mBiddingEvaluationDTO1).isNotEqualTo(mBiddingEvaluationDTO2);
        mBiddingEvaluationDTO2.setId(mBiddingEvaluationDTO1.getId());
        assertThat(mBiddingEvaluationDTO1).isEqualTo(mBiddingEvaluationDTO2);
        mBiddingEvaluationDTO2.setId(2L);
        assertThat(mBiddingEvaluationDTO1).isNotEqualTo(mBiddingEvaluationDTO2);
        mBiddingEvaluationDTO1.setId(null);
        assertThat(mBiddingEvaluationDTO1).isNotEqualTo(mBiddingEvaluationDTO2);
    }
}
