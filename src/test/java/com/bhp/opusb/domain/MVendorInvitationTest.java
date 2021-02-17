package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorInvitationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorInvitation.class);
        MVendorInvitation mVendorInvitation1 = new MVendorInvitation();
        mVendorInvitation1.setId(1L);
        MVendorInvitation mVendorInvitation2 = new MVendorInvitation();
        mVendorInvitation2.setId(mVendorInvitation1.getId());
        assertThat(mVendorInvitation1).isEqualTo(mVendorInvitation2);
        mVendorInvitation2.setId(2L);
        assertThat(mVendorInvitation1).isNotEqualTo(mVendorInvitation2);
        mVendorInvitation1.setId(null);
        assertThat(mVendorInvitation1).isNotEqualTo(mVendorInvitation2);
    }
}
