package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingDTO.class);
        MBiddingDTO mBiddingDTO1 = new MBiddingDTO();
        mBiddingDTO1.setId(1L);
        MBiddingDTO mBiddingDTO2 = new MBiddingDTO();
        assertThat(mBiddingDTO1).isNotEqualTo(mBiddingDTO2);
        mBiddingDTO2.setId(mBiddingDTO1.getId());
        assertThat(mBiddingDTO1).isEqualTo(mBiddingDTO2);
        mBiddingDTO2.setId(2L);
        assertThat(mBiddingDTO1).isNotEqualTo(mBiddingDTO2);
        mBiddingDTO1.setId(null);
        assertThat(mBiddingDTO1).isNotEqualTo(mBiddingDTO2);
    }
}
