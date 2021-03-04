package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingSubItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingSubItemDTO.class);
        MBiddingSubItemDTO mBiddingSubItemDTO1 = new MBiddingSubItemDTO();
        mBiddingSubItemDTO1.setId(1L);
        MBiddingSubItemDTO mBiddingSubItemDTO2 = new MBiddingSubItemDTO();
        assertThat(mBiddingSubItemDTO1).isNotEqualTo(mBiddingSubItemDTO2);
        mBiddingSubItemDTO2.setId(mBiddingSubItemDTO1.getId());
        assertThat(mBiddingSubItemDTO1).isEqualTo(mBiddingSubItemDTO2);
        mBiddingSubItemDTO2.setId(2L);
        assertThat(mBiddingSubItemDTO1).isNotEqualTo(mBiddingSubItemDTO2);
        mBiddingSubItemDTO1.setId(null);
        assertThat(mBiddingSubItemDTO1).isNotEqualTo(mBiddingSubItemDTO2);
    }
}
