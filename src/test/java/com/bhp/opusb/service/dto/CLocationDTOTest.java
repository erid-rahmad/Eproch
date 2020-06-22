package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CLocationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CLocationDTO.class);
        CLocationDTO cLocationDTO1 = new CLocationDTO();
        cLocationDTO1.setId(1L);
        CLocationDTO cLocationDTO2 = new CLocationDTO();
        assertThat(cLocationDTO1).isNotEqualTo(cLocationDTO2);
        cLocationDTO2.setId(cLocationDTO1.getId());
        assertThat(cLocationDTO1).isEqualTo(cLocationDTO2);
        cLocationDTO2.setId(2L);
        assertThat(cLocationDTO1).isNotEqualTo(cLocationDTO2);
        cLocationDTO1.setId(null);
        assertThat(cLocationDTO1).isNotEqualTo(cLocationDTO2);
    }
}
