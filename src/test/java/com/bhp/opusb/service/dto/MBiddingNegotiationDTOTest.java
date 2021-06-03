package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingNegotiationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingNegotiationDTO.class);
        MBiddingNegotiationDTO mBiddingNegotiationDTO1 = new MBiddingNegotiationDTO();
        mBiddingNegotiationDTO1.setId(1L);
        MBiddingNegotiationDTO mBiddingNegotiationDTO2 = new MBiddingNegotiationDTO();
        assertThat(mBiddingNegotiationDTO1).isNotEqualTo(mBiddingNegotiationDTO2);
        mBiddingNegotiationDTO2.setId(mBiddingNegotiationDTO1.getId());
        assertThat(mBiddingNegotiationDTO1).isEqualTo(mBiddingNegotiationDTO2);
        mBiddingNegotiationDTO2.setId(2L);
        assertThat(mBiddingNegotiationDTO1).isNotEqualTo(mBiddingNegotiationDTO2);
        mBiddingNegotiationDTO1.setId(null);
        assertThat(mBiddingNegotiationDTO1).isNotEqualTo(mBiddingNegotiationDTO2);
    }
}
