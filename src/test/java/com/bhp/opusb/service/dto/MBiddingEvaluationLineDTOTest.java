package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvaluationLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvaluationLineDTO.class);
        MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO1 = new MBiddingEvaluationLineDTO();
        mBiddingEvaluationLineDTO1.setId(1L);
        MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO2 = new MBiddingEvaluationLineDTO();
        assertThat(mBiddingEvaluationLineDTO1).isNotEqualTo(mBiddingEvaluationLineDTO2);
        mBiddingEvaluationLineDTO2.setId(mBiddingEvaluationLineDTO1.getId());
        assertThat(mBiddingEvaluationLineDTO1).isEqualTo(mBiddingEvaluationLineDTO2);
        mBiddingEvaluationLineDTO2.setId(2L);
        assertThat(mBiddingEvaluationLineDTO1).isNotEqualTo(mBiddingEvaluationLineDTO2);
        mBiddingEvaluationLineDTO1.setId(null);
        assertThat(mBiddingEvaluationLineDTO1).isNotEqualTo(mBiddingEvaluationLineDTO2);
    }
}
