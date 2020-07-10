package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTaskDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTaskDTO.class);
        AdTaskDTO adTaskDTO1 = new AdTaskDTO();
        adTaskDTO1.setId(1L);
        AdTaskDTO adTaskDTO2 = new AdTaskDTO();
        assertThat(adTaskDTO1).isNotEqualTo(adTaskDTO2);
        adTaskDTO2.setId(adTaskDTO1.getId());
        assertThat(adTaskDTO1).isEqualTo(adTaskDTO2);
        adTaskDTO2.setId(2L);
        assertThat(adTaskDTO1).isNotEqualTo(adTaskDTO2);
        adTaskDTO1.setId(null);
        assertThat(adTaskDTO1).isNotEqualTo(adTaskDTO2);
    }
}
