package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CTaxRateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CTaxRate.class);
        CTaxRate cTaxRate1 = new CTaxRate();
        cTaxRate1.setId(1L);
        CTaxRate cTaxRate2 = new CTaxRate();
        cTaxRate2.setId(cTaxRate1.getId());
        assertThat(cTaxRate1).isEqualTo(cTaxRate2);
        cTaxRate2.setId(2L);
        assertThat(cTaxRate1).isNotEqualTo(cTaxRate2);
        cTaxRate1.setId(null);
        assertThat(cTaxRate1).isNotEqualTo(cTaxRate2);
    }
}
