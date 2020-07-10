package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdButtonTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdButton.class);
        AdButton adButton1 = new AdButton();
        adButton1.setId(1L);
        AdButton adButton2 = new AdButton();
        adButton2.setId(adButton1.getId());
        assertThat(adButton1).isEqualTo(adButton2);
        adButton2.setId(2L);
        assertThat(adButton1).isNotEqualTo(adButton2);
        adButton1.setId(null);
        assertThat(adButton1).isNotEqualTo(adButton2);
    }
}
