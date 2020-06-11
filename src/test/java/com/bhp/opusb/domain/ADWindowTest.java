package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADWindowTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADWindow.class);
        ADWindow aDWindow1 = new ADWindow();
        aDWindow1.setId(1L);
        ADWindow aDWindow2 = new ADWindow();
        aDWindow2.setId(aDWindow1.getId());
        assertThat(aDWindow1).isEqualTo(aDWindow2);
        aDWindow2.setId(2L);
        assertThat(aDWindow1).isNotEqualTo(aDWindow2);
        aDWindow1.setId(null);
        assertThat(aDWindow1).isNotEqualTo(aDWindow2);
    }
}
