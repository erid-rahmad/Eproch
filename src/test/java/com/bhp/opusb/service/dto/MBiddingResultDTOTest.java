package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingResultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingResultDTO.class);
        MBiddingResultDTO mBiddingResultDTO1 = new MBiddingResultDTO();
        mBiddingResultDTO1.setId(1L);
        MBiddingResultDTO mBiddingResultDTO2 = new MBiddingResultDTO();
        assertThat(mBiddingResultDTO1).isNotEqualTo(mBiddingResultDTO2);
        mBiddingResultDTO2.setId(mBiddingResultDTO1.getId());
        assertThat(mBiddingResultDTO1).isEqualTo(mBiddingResultDTO2);
        mBiddingResultDTO2.setId(2L);
        assertThat(mBiddingResultDTO1).isNotEqualTo(mBiddingResultDTO2);
        mBiddingResultDTO1.setId(null);
        assertThat(mBiddingResultDTO1).isNotEqualTo(mBiddingResultDTO2);
    }
}
