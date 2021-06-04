package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingNegotiationLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingNegotiationLineDTO.class);
        MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO1 = new MBiddingNegotiationLineDTO();
        mBiddingNegotiationLineDTO1.setId(1L);
        MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO2 = new MBiddingNegotiationLineDTO();
        assertThat(mBiddingNegotiationLineDTO1).isNotEqualTo(mBiddingNegotiationLineDTO2);
        mBiddingNegotiationLineDTO2.setId(mBiddingNegotiationLineDTO1.getId());
        assertThat(mBiddingNegotiationLineDTO1).isEqualTo(mBiddingNegotiationLineDTO2);
        mBiddingNegotiationLineDTO2.setId(2L);
        assertThat(mBiddingNegotiationLineDTO1).isNotEqualTo(mBiddingNegotiationLineDTO2);
        mBiddingNegotiationLineDTO1.setId(null);
        assertThat(mBiddingNegotiationLineDTO1).isNotEqualTo(mBiddingNegotiationLineDTO2);
    }
}
