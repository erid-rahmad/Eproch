package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class PaDashboardItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaDashboardItemDTO.class);
        PaDashboardItemDTO paDashboardItemDTO1 = new PaDashboardItemDTO();
        paDashboardItemDTO1.setId(1L);
        PaDashboardItemDTO paDashboardItemDTO2 = new PaDashboardItemDTO();
        assertThat(paDashboardItemDTO1).isNotEqualTo(paDashboardItemDTO2);
        paDashboardItemDTO2.setId(paDashboardItemDTO1.getId());
        assertThat(paDashboardItemDTO1).isEqualTo(paDashboardItemDTO2);
        paDashboardItemDTO2.setId(2L);
        assertThat(paDashboardItemDTO1).isNotEqualTo(paDashboardItemDTO2);
        paDashboardItemDTO1.setId(null);
        assertThat(paDashboardItemDTO1).isNotEqualTo(paDashboardItemDTO2);
    }
}
