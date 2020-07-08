package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTaskSchedulerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTaskScheduler.class);
        AdTaskScheduler adTaskScheduler1 = new AdTaskScheduler();
        adTaskScheduler1.setId(1L);
        AdTaskScheduler adTaskScheduler2 = new AdTaskScheduler();
        adTaskScheduler2.setId(adTaskScheduler1.getId());
        assertThat(adTaskScheduler1).isEqualTo(adTaskScheduler2);
        adTaskScheduler2.setId(2L);
        assertThat(adTaskScheduler1).isNotEqualTo(adTaskScheduler2);
        adTaskScheduler1.setId(null);
        assertThat(adTaskScheduler1).isNotEqualTo(adTaskScheduler2);
    }
}
