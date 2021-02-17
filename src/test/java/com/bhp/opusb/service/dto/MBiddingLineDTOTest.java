package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingLineDTO.class);
        MBiddingLineDTO mBiddingLineDTO1 = new MBiddingLineDTO();
        mBiddingLineDTO1.setId(1L);
        MBiddingLineDTO mBiddingLineDTO2 = new MBiddingLineDTO();
        assertThat(mBiddingLineDTO1).isNotEqualTo(mBiddingLineDTO2);
        mBiddingLineDTO2.setId(mBiddingLineDTO1.getId());
        assertThat(mBiddingLineDTO1).isEqualTo(mBiddingLineDTO2);
        mBiddingLineDTO2.setId(2L);
        assertThat(mBiddingLineDTO1).isNotEqualTo(mBiddingLineDTO2);
        mBiddingLineDTO1.setId(null);
        assertThat(mBiddingLineDTO1).isNotEqualTo(mBiddingLineDTO2);
    }
}
