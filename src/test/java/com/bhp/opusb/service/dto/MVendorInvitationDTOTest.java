package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorInvitationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorInvitationDTO.class);
        MVendorInvitationDTO mVendorInvitationDTO1 = new MVendorInvitationDTO();
        mVendorInvitationDTO1.setId(1L);
        MVendorInvitationDTO mVendorInvitationDTO2 = new MVendorInvitationDTO();
        assertThat(mVendorInvitationDTO1).isNotEqualTo(mVendorInvitationDTO2);
        mVendorInvitationDTO2.setId(mVendorInvitationDTO1.getId());
        assertThat(mVendorInvitationDTO1).isEqualTo(mVendorInvitationDTO2);
        mVendorInvitationDTO2.setId(2L);
        assertThat(mVendorInvitationDTO1).isNotEqualTo(mVendorInvitationDTO2);
        mVendorInvitationDTO1.setId(null);
        assertThat(mVendorInvitationDTO1).isNotEqualTo(mVendorInvitationDTO2);
    }
}
