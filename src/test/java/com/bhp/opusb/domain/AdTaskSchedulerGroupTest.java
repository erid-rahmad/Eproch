package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTaskSchedulerGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTaskSchedulerGroup.class);
        AdTaskSchedulerGroup adTaskSchedulerGroup1 = new AdTaskSchedulerGroup();
        adTaskSchedulerGroup1.setId(1L);
        AdTaskSchedulerGroup adTaskSchedulerGroup2 = new AdTaskSchedulerGroup();
        adTaskSchedulerGroup2.setId(adTaskSchedulerGroup1.getId());
        assertThat(adTaskSchedulerGroup1).isEqualTo(adTaskSchedulerGroup2);
        adTaskSchedulerGroup2.setId(2L);
        assertThat(adTaskSchedulerGroup1).isNotEqualTo(adTaskSchedulerGroup2);
        adTaskSchedulerGroup1.setId(null);
        assertThat(adTaskSchedulerGroup1).isNotEqualTo(adTaskSchedulerGroup2);
    }
}
