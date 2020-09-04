package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CElementValueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CElementValueDTO.class);
        CElementValueDTO cElementValueDTO1 = new CElementValueDTO();
        cElementValueDTO1.setId(1L);
        CElementValueDTO cElementValueDTO2 = new CElementValueDTO();
        assertThat(cElementValueDTO1).isNotEqualTo(cElementValueDTO2);
        cElementValueDTO2.setId(cElementValueDTO1.getId());
        assertThat(cElementValueDTO1).isEqualTo(cElementValueDTO2);
        cElementValueDTO2.setId(2L);
        assertThat(cElementValueDTO1).isNotEqualTo(cElementValueDTO2);
        cElementValueDTO1.setId(null);
        assertThat(cElementValueDTO1).isNotEqualTo(cElementValueDTO2);
    }
}
