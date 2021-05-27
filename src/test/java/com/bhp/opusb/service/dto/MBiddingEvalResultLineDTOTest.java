package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingEvalResultLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingEvalResultLineDTO.class);
        MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO1 = new MBiddingEvalResultLineDTO();
        mBiddingEvalResultLineDTO1.setId(1L);
        MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO2 = new MBiddingEvalResultLineDTO();
        assertThat(mBiddingEvalResultLineDTO1).isNotEqualTo(mBiddingEvalResultLineDTO2);
        mBiddingEvalResultLineDTO2.setId(mBiddingEvalResultLineDTO1.getId());
        assertThat(mBiddingEvalResultLineDTO1).isEqualTo(mBiddingEvalResultLineDTO2);
        mBiddingEvalResultLineDTO2.setId(2L);
        assertThat(mBiddingEvalResultLineDTO1).isNotEqualTo(mBiddingEvalResultLineDTO2);
        mBiddingEvalResultLineDTO1.setId(null);
        assertThat(mBiddingEvalResultLineDTO1).isNotEqualTo(mBiddingEvalResultLineDTO2);
    }
}
