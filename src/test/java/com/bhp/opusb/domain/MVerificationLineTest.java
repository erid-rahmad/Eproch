package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVerificationLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVerificationLine.class);
        MVerificationLine mVerificationLine1 = new MVerificationLine();
        mVerificationLine1.setId(1L);
        MVerificationLine mVerificationLine2 = new MVerificationLine();
        mVerificationLine2.setId(mVerificationLine1.getId());
        assertThat(mVerificationLine1).isEqualTo(mVerificationLine2);
        mVerificationLine2.setId(2L);
        assertThat(mVerificationLine1).isNotEqualTo(mVerificationLine2);
        mVerificationLine1.setId(null);
        assertThat(mVerificationLine1).isNotEqualTo(mVerificationLine2);
    }
}
