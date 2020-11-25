package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVerificationTaxTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVerificationTax.class);
        MVerificationTax mVerificationTax1 = new MVerificationTax();
        mVerificationTax1.setId(1L);
        MVerificationTax mVerificationTax2 = new MVerificationTax();
        mVerificationTax2.setId(mVerificationTax1.getId());
        assertThat(mVerificationTax1).isEqualTo(mVerificationTax2);
        mVerificationTax2.setId(2L);
        assertThat(mVerificationTax1).isNotEqualTo(mVerificationTax2);
        mVerificationTax1.setId(null);
        assertThat(mVerificationTax1).isNotEqualTo(mVerificationTax2);
    }
}
