package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPeriodTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPeriod.class);
        CPeriod cPeriod1 = new CPeriod();
        cPeriod1.setId(1L);
        CPeriod cPeriod2 = new CPeriod();
        cPeriod2.setId(cPeriod1.getId());
        assertThat(cPeriod1).isEqualTo(cPeriod2);
        cPeriod2.setId(2L);
        assertThat(cPeriod1).isNotEqualTo(cPeriod2);
        cPeriod1.setId(null);
        assertThat(cPeriod1).isNotEqualTo(cPeriod2);
    }
}
