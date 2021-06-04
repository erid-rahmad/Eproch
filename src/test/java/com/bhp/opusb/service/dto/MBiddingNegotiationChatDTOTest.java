package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingNegotiationChatDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingNegotiationChatDTO.class);
        MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO1 = new MBiddingNegotiationChatDTO();
        mBiddingNegotiationChatDTO1.setId(1L);
        MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO2 = new MBiddingNegotiationChatDTO();
        assertThat(mBiddingNegotiationChatDTO1).isNotEqualTo(mBiddingNegotiationChatDTO2);
        mBiddingNegotiationChatDTO2.setId(mBiddingNegotiationChatDTO1.getId());
        assertThat(mBiddingNegotiationChatDTO1).isEqualTo(mBiddingNegotiationChatDTO2);
        mBiddingNegotiationChatDTO2.setId(2L);
        assertThat(mBiddingNegotiationChatDTO1).isNotEqualTo(mBiddingNegotiationChatDTO2);
        mBiddingNegotiationChatDTO1.setId(null);
        assertThat(mBiddingNegotiationChatDTO1).isNotEqualTo(mBiddingNegotiationChatDTO2);
    }
}
