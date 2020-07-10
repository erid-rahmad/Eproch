package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTaskTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTask.class);
        AdTask adTask1 = new AdTask();
        adTask1.setId(1L);
        AdTask adTask2 = new AdTask();
        adTask2.setId(adTask1.getId());
        assertThat(adTask1).isEqualTo(adTask2);
        adTask2.setId(2L);
        assertThat(adTask1).isNotEqualTo(adTask2);
        adTask1.setId(null);
        assertThat(adTask1).isNotEqualTo(adTask2);
    }
}
