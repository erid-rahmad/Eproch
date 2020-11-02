package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CConvertionRateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CConvertionRate.class);
        CConvertionRate cConvertionRate1 = new CConvertionRate();
        cConvertionRate1.setId(1L);
        CConvertionRate cConvertionRate2 = new CConvertionRate();
        cConvertionRate2.setId(cConvertionRate1.getId());
        assertThat(cConvertionRate1).isEqualTo(cConvertionRate2);
        cConvertionRate2.setId(2L);
        assertThat(cConvertionRate1).isNotEqualTo(cConvertionRate2);
        cConvertionRate1.setId(null);
        assertThat(cConvertionRate1).isNotEqualTo(cConvertionRate2);
    }
}
