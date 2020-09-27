package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdCalloutTargetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdCalloutTarget.class);
        AdCalloutTarget adCalloutTarget1 = new AdCalloutTarget();
        adCalloutTarget1.setId(1L);
        AdCalloutTarget adCalloutTarget2 = new AdCalloutTarget();
        adCalloutTarget2.setId(adCalloutTarget1.getId());
        assertThat(adCalloutTarget1).isEqualTo(adCalloutTarget2);
        adCalloutTarget2.setId(2L);
        assertThat(adCalloutTarget1).isNotEqualTo(adCalloutTarget2);
        adCalloutTarget1.setId(null);
        assertThat(adCalloutTarget1).isNotEqualTo(adCalloutTarget2);
    }
}
