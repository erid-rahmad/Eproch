package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvalResultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvalResultDTO.class);
        MBiddingEvalResultDTO mBiddingEvalResultDTO1 = new MBiddingEvalResultDTO();
        mBiddingEvalResultDTO1.setId(1L);
        MBiddingEvalResultDTO mBiddingEvalResultDTO2 = new MBiddingEvalResultDTO();
        assertThat(mBiddingEvalResultDTO1).isNotEqualTo(mBiddingEvalResultDTO2);
        mBiddingEvalResultDTO2.setId(mBiddingEvalResultDTO1.getId());
        assertThat(mBiddingEvalResultDTO1).isEqualTo(mBiddingEvalResultDTO2);
        mBiddingEvalResultDTO2.setId(2L);
        assertThat(mBiddingEvalResultDTO1).isNotEqualTo(mBiddingEvalResultDTO2);
        mBiddingEvalResultDTO1.setId(null);
        assertThat(mBiddingEvalResultDTO1).isNotEqualTo(mBiddingEvalResultDTO2);
    }
}
