package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdFormTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdForm.class);
        AdForm adForm1 = new AdForm();
        adForm1.setId(1L);
        AdForm adForm2 = new AdForm();
        adForm2.setId(adForm1.getId());
        assertThat(adForm1).isEqualTo(adForm2);
        adForm2.setId(2L);
        assertThat(adForm1).isNotEqualTo(adForm2);
        adForm1.setId(null);
        assertThat(adForm1).isNotEqualTo(adForm2);
    }
}
