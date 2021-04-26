package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingInvitationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingInvitationDTO.class);
        MBiddingInvitationDTO mBiddingInvitationDTO1 = new MBiddingInvitationDTO();
        mBiddingInvitationDTO1.setId(1L);
        MBiddingInvitationDTO mBiddingInvitationDTO2 = new MBiddingInvitationDTO();
        assertThat(mBiddingInvitationDTO1).isNotEqualTo(mBiddingInvitationDTO2);
        mBiddingInvitationDTO2.setId(mBiddingInvitationDTO1.getId());
        assertThat(mBiddingInvitationDTO1).isEqualTo(mBiddingInvitationDTO2);
        mBiddingInvitationDTO2.setId(2L);
        assertThat(mBiddingInvitationDTO1).isNotEqualTo(mBiddingInvitationDTO2);
        mBiddingInvitationDTO1.setId(null);
        assertThat(mBiddingInvitationDTO1).isNotEqualTo(mBiddingInvitationDTO2);
    }
}
