package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdMenuTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdMenu.class);
        AdMenu adMenu1 = new AdMenu();
        adMenu1.setId(1L);
        AdMenu adMenu2 = new AdMenu();
        adMenu2.setId(adMenu1.getId());
        assertThat(adMenu1).isEqualTo(adMenu2);
        adMenu2.setId(2L);
        assertThat(adMenu1).isNotEqualTo(adMenu2);
        adMenu1.setId(null);
        assertThat(adMenu1).isNotEqualTo(adMenu2);
    }
}
