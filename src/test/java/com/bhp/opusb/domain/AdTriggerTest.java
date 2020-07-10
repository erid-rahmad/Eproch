package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTriggerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTrigger.class);
        AdTrigger adTrigger1 = new AdTrigger();
        adTrigger1.setId(1L);
        AdTrigger adTrigger2 = new AdTrigger();
        adTrigger2.setId(adTrigger1.getId());
        assertThat(adTrigger1).isEqualTo(adTrigger2);
        adTrigger2.setId(2L);
        assertThat(adTrigger1).isNotEqualTo(adTrigger2);
        adTrigger1.setId(null);
        assertThat(adTrigger1).isNotEqualTo(adTrigger2);
    }
}
