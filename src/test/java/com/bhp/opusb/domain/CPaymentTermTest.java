package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPaymentTermTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPaymentTerm.class);
        CPaymentTerm cPaymentTerm1 = new CPaymentTerm();
        cPaymentTerm1.setId(1L);
        CPaymentTerm cPaymentTerm2 = new CPaymentTerm();
        cPaymentTerm2.setId(cPaymentTerm1.getId());
        assertThat(cPaymentTerm1).isEqualTo(cPaymentTerm2);
        cPaymentTerm2.setId(2L);
        assertThat(cPaymentTerm1).isNotEqualTo(cPaymentTerm2);
        cPaymentTerm1.setId(null);
        assertThat(cPaymentTerm1).isNotEqualTo(cPaymentTerm2);
    }
}
