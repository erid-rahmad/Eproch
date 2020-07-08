package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTaskProcessTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTaskProcess.class);
        AdTaskProcess adTaskProcess1 = new AdTaskProcess();
        adTaskProcess1.setId(1L);
        AdTaskProcess adTaskProcess2 = new AdTaskProcess();
        adTaskProcess2.setId(adTaskProcess1.getId());
        assertThat(adTaskProcess1).isEqualTo(adTaskProcess2);
        adTaskProcess2.setId(2L);
        assertThat(adTaskProcess1).isNotEqualTo(adTaskProcess2);
        adTaskProcess1.setId(null);
        assertThat(adTaskProcess1).isNotEqualTo(adTaskProcess2);
    }
}
