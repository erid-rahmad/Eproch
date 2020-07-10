package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTriggerParamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTriggerParam.class);
        AdTriggerParam adTriggerParam1 = new AdTriggerParam();
        adTriggerParam1.setId(1L);
        AdTriggerParam adTriggerParam2 = new AdTriggerParam();
        adTriggerParam2.setId(adTriggerParam1.getId());
        assertThat(adTriggerParam1).isEqualTo(adTriggerParam2);
        adTriggerParam2.setId(2L);
        assertThat(adTriggerParam1).isNotEqualTo(adTriggerParam2);
        adTriggerParam1.setId(null);
        assertThat(adTriggerParam1).isNotEqualTo(adTriggerParam2);
    }
}
