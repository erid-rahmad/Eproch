package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationInvitationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationInvitation.class);
        MPrequalificationInvitation mPrequalificationInvitation1 = new MPrequalificationInvitation();
        mPrequalificationInvitation1.setId(1L);
        MPrequalificationInvitation mPrequalificationInvitation2 = new MPrequalificationInvitation();
        mPrequalificationInvitation2.setId(mPrequalificationInvitation1.getId());
        assertThat(mPrequalificationInvitation1).isEqualTo(mPrequalificationInvitation2);
        mPrequalificationInvitation2.setId(2L);
        assertThat(mPrequalificationInvitation1).isNotEqualTo(mPrequalificationInvitation2);
        mPrequalificationInvitation1.setId(null);
        assertThat(mPrequalificationInvitation1).isNotEqualTo(mPrequalificationInvitation2);
    }
}
