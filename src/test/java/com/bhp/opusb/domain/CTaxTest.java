package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CTaxTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CTax.class);
        CTax cTax1 = new CTax();
        cTax1.setId(1L);
        CTax cTax2 = new CTax();
        cTax2.setId(cTax1.getId());
        assertThat(cTax1).isEqualTo(cTax2);
        cTax2.setId(2L);
        assertThat(cTax1).isNotEqualTo(cTax2);
        cTax1.setId(null);
        assertThat(cTax1).isNotEqualTo(cTax2);
    }
}
