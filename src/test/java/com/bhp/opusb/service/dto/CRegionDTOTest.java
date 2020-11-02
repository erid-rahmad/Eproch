package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CRegionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRegionDTO.class);
        CRegionDTO cRegionDTO1 = new CRegionDTO();
        cRegionDTO1.setId(1L);
        CRegionDTO cRegionDTO2 = new CRegionDTO();
        assertThat(cRegionDTO1).isNotEqualTo(cRegionDTO2);
        cRegionDTO2.setId(cRegionDTO1.getId());
        assertThat(cRegionDTO1).isEqualTo(cRegionDTO2);
        cRegionDTO2.setId(2L);
        assertThat(cRegionDTO1).isNotEqualTo(cRegionDTO2);
        cRegionDTO1.setId(null);
        assertThat(cRegionDTO1).isNotEqualTo(cRegionDTO2);
    }
}
