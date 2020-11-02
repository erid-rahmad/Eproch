package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CLocation.class);
        CLocation cLocation1 = new CLocation();
        cLocation1.setId(1L);
        CLocation cLocation2 = new CLocation();
        cLocation2.setId(cLocation1.getId());
        assertThat(cLocation1).isEqualTo(cLocation2);
        cLocation2.setId(2L);
        assertThat(cLocation1).isNotEqualTo(cLocation2);
        cLocation1.setId(null);
        assertThat(cLocation1).isNotEqualTo(cLocation2);
    }
}
