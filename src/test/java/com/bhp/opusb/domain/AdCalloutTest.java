package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdCalloutTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdCallout.class);
        AdCallout adCallout1 = new AdCallout();
        adCallout1.setId(1L);
        AdCallout adCallout2 = new AdCallout();
        adCallout2.setId(adCallout1.getId());
        assertThat(adCallout1).isEqualTo(adCallout2);
        adCallout2.setId(2L);
        assertThat(adCallout1).isNotEqualTo(adCallout2);
        adCallout1.setId(null);
        assertThat(adCallout1).isNotEqualTo(adCallout2);
    }
}
