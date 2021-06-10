package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionInvitationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionInvitationDTO.class);
        MAuctionInvitationDTO mAuctionInvitationDTO1 = new MAuctionInvitationDTO();
        mAuctionInvitationDTO1.setId(1L);
        MAuctionInvitationDTO mAuctionInvitationDTO2 = new MAuctionInvitationDTO();
        assertThat(mAuctionInvitationDTO1).isNotEqualTo(mAuctionInvitationDTO2);
        mAuctionInvitationDTO2.setId(mAuctionInvitationDTO1.getId());
        assertThat(mAuctionInvitationDTO1).isEqualTo(mAuctionInvitationDTO2);
        mAuctionInvitationDTO2.setId(2L);
        assertThat(mAuctionInvitationDTO1).isNotEqualTo(mAuctionInvitationDTO2);
        mAuctionInvitationDTO1.setId(null);
        assertThat(mAuctionInvitationDTO1).isNotEqualTo(mAuctionInvitationDTO2);
    }
}
