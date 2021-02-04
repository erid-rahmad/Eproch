package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class PaDashboardPreferenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaDashboardPreference.class);
        PaDashboardPreference paDashboardPreference1 = new PaDashboardPreference();
        paDashboardPreference1.setId(1L);
        PaDashboardPreference paDashboardPreference2 = new PaDashboardPreference();
        paDashboardPreference2.setId(paDashboardPreference1.getId());
        assertThat(paDashboardPreference1).isEqualTo(paDashboardPreference2);
        paDashboardPreference2.setId(2L);
        assertThat(paDashboardPreference1).isNotEqualTo(paDashboardPreference2);
        paDashboardPreference1.setId(null);
        assertThat(paDashboardPreference1).isNotEqualTo(paDashboardPreference2);
    }
}
