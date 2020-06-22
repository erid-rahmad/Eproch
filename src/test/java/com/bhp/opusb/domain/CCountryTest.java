package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CCountryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCountry.class);
        CCountry cCountry1 = new CCountry();
        cCountry1.setId(1L);
        CCountry cCountry2 = new CCountry();
        cCountry2.setId(cCountry1.getId());
        assertThat(cCountry1).isEqualTo(cCountry2);
        cCountry2.setId(2L);
        assertThat(cCountry1).isNotEqualTo(cCountry2);
        cCountry1.setId(null);
        assertThat(cCountry1).isNotEqualTo(cCountry2);
    }
}
