package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPersonInChargeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPersonInCharge.class);
        CPersonInCharge cPersonInCharge1 = new CPersonInCharge();
        cPersonInCharge1.setId(1L);
        CPersonInCharge cPersonInCharge2 = new CPersonInCharge();
        cPersonInCharge2.setId(cPersonInCharge1.getId());
        assertThat(cPersonInCharge1).isEqualTo(cPersonInCharge2);
        cPersonInCharge2.setId(2L);
        assertThat(cPersonInCharge1).isNotEqualTo(cPersonInCharge2);
        cPersonInCharge1.setId(null);
        assertThat(cPersonInCharge1).isNotEqualTo(cPersonInCharge2);
    }
}
