package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CElementValueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CElementValue.class);
        CElementValue cElementValue1 = new CElementValue();
        cElementValue1.setId(1L);
        CElementValue cElementValue2 = new CElementValue();
        cElementValue2.setId(cElementValue1.getId());
        assertThat(cElementValue1).isEqualTo(cElementValue2);
        cElementValue2.setId(2L);
        assertThat(cElementValue1).isNotEqualTo(cElementValue2);
        cElementValue1.setId(null);
        assertThat(cElementValue1).isNotEqualTo(cElementValue2);
    }
}
