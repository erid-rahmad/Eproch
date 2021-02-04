package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class PaDashboardPreferenceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaDashboardPreferenceDTO.class);
        PaDashboardPreferenceDTO paDashboardPreferenceDTO1 = new PaDashboardPreferenceDTO();
        paDashboardPreferenceDTO1.setId(1L);
        PaDashboardPreferenceDTO paDashboardPreferenceDTO2 = new PaDashboardPreferenceDTO();
        assertThat(paDashboardPreferenceDTO1).isNotEqualTo(paDashboardPreferenceDTO2);
        paDashboardPreferenceDTO2.setId(paDashboardPreferenceDTO1.getId());
        assertThat(paDashboardPreferenceDTO1).isEqualTo(paDashboardPreferenceDTO2);
        paDashboardPreferenceDTO2.setId(2L);
        assertThat(paDashboardPreferenceDTO1).isNotEqualTo(paDashboardPreferenceDTO2);
        paDashboardPreferenceDTO1.setId(null);
        assertThat(paDashboardPreferenceDTO1).isNotEqualTo(paDashboardPreferenceDTO2);
    }
}
