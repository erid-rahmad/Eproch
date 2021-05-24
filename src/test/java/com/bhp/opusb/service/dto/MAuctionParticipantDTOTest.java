package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionParticipantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionParticipantDTO.class);
        MAuctionParticipantDTO mAuctionParticipantDTO1 = new MAuctionParticipantDTO();
        mAuctionParticipantDTO1.setId(1L);
        MAuctionParticipantDTO mAuctionParticipantDTO2 = new MAuctionParticipantDTO();
        assertThat(mAuctionParticipantDTO1).isNotEqualTo(mAuctionParticipantDTO2);
        mAuctionParticipantDTO2.setId(mAuctionParticipantDTO1.getId());
        assertThat(mAuctionParticipantDTO1).isEqualTo(mAuctionParticipantDTO2);
        mAuctionParticipantDTO2.setId(2L);
        assertThat(mAuctionParticipantDTO1).isNotEqualTo(mAuctionParticipantDTO2);
        mAuctionParticipantDTO1.setId(null);
        assertThat(mAuctionParticipantDTO1).isNotEqualTo(mAuctionParticipantDTO2);
    }
}
