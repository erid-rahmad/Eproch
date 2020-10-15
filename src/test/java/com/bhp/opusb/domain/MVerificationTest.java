package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVerificationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVerification.class);
        MVerification mVerification1 = new MVerification();
        mVerification1.setId(1L);
        MVerification mVerification2 = new MVerification();
        mVerification2.setId(mVerification1.getId());
        assertThat(mVerification1).isEqualTo(mVerification2);
        mVerification2.setId(2L);
        assertThat(mVerification1).isNotEqualTo(mVerification2);
        mVerification1.setId(null);
        assertThat(mVerification1).isNotEqualTo(mVerification2);
    }
}
