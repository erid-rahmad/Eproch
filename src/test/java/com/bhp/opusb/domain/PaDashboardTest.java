package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class PaDashboardTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaDashboard.class);
        PaDashboard paDashboard1 = new PaDashboard();
        paDashboard1.setId(1L);
        PaDashboard paDashboard2 = new PaDashboard();
        paDashboard2.setId(paDashboard1.getId());
        assertThat(paDashboard1).isEqualTo(paDashboard2);
        paDashboard2.setId(2L);
        assertThat(paDashboard1).isNotEqualTo(paDashboard2);
        paDashboard1.setId(null);
        assertThat(paDashboard1).isNotEqualTo(paDashboard2);
    }
}
