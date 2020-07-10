package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTaskApplicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTaskApplication.class);
        AdTaskApplication adTaskApplication1 = new AdTaskApplication();
        adTaskApplication1.setId(1L);
        AdTaskApplication adTaskApplication2 = new AdTaskApplication();
        adTaskApplication2.setId(adTaskApplication1.getId());
        assertThat(adTaskApplication1).isEqualTo(adTaskApplication2);
        adTaskApplication2.setId(2L);
        assertThat(adTaskApplication1).isNotEqualTo(adTaskApplication2);
        adTaskApplication1.setId(null);
        assertThat(adTaskApplication1).isNotEqualTo(adTaskApplication2);
    }
}
