package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CTaskTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CTask.class);
        CTask cTask1 = new CTask();
        cTask1.setId(1L);
        CTask cTask2 = new CTask();
        cTask2.setId(cTask1.getId());
        assertThat(cTask1).isEqualTo(cTask2);
        cTask2.setId(2L);
        assertThat(cTask1).isNotEqualTo(cTask2);
        cTask1.setId(null);
        assertThat(cTask1).isNotEqualTo(cTask2);
    }
}
