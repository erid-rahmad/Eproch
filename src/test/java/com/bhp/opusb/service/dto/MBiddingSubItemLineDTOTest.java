package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingSubItemLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingSubItemLineDTO.class);
        MBiddingSubItemLineDTO mBiddingSubItemLineDTO1 = new MBiddingSubItemLineDTO();
        mBiddingSubItemLineDTO1.setId(1L);
        MBiddingSubItemLineDTO mBiddingSubItemLineDTO2 = new MBiddingSubItemLineDTO();
        assertThat(mBiddingSubItemLineDTO1).isNotEqualTo(mBiddingSubItemLineDTO2);
        mBiddingSubItemLineDTO2.setId(mBiddingSubItemLineDTO1.getId());
        assertThat(mBiddingSubItemLineDTO1).isEqualTo(mBiddingSubItemLineDTO2);
        mBiddingSubItemLineDTO2.setId(2L);
        assertThat(mBiddingSubItemLineDTO1).isNotEqualTo(mBiddingSubItemLineDTO2);
        mBiddingSubItemLineDTO1.setId(null);
        assertThat(mBiddingSubItemLineDTO1).isNotEqualTo(mBiddingSubItemLineDTO2);
    }
}
