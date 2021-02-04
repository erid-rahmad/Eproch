package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class PaDashboardDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaDashboardDTO.class);
        PaDashboardDTO paDashboardDTO1 = new PaDashboardDTO();
        paDashboardDTO1.setId(1L);
        PaDashboardDTO paDashboardDTO2 = new PaDashboardDTO();
        assertThat(paDashboardDTO1).isNotEqualTo(paDashboardDTO2);
        paDashboardDTO2.setId(paDashboardDTO1.getId());
        assertThat(paDashboardDTO1).isEqualTo(paDashboardDTO2);
        paDashboardDTO2.setId(2L);
        assertThat(paDashboardDTO1).isNotEqualTo(paDashboardDTO2);
        paDashboardDTO1.setId(null);
        assertThat(paDashboardDTO1).isNotEqualTo(paDashboardDTO2);
    }
}
