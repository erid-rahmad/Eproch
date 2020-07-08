package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTaskSchedulerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTaskSchedulerDTO.class);
        AdTaskSchedulerDTO adTaskSchedulerDTO1 = new AdTaskSchedulerDTO();
        adTaskSchedulerDTO1.setId(1L);
        AdTaskSchedulerDTO adTaskSchedulerDTO2 = new AdTaskSchedulerDTO();
        assertThat(adTaskSchedulerDTO1).isNotEqualTo(adTaskSchedulerDTO2);
        adTaskSchedulerDTO2.setId(adTaskSchedulerDTO1.getId());
        assertThat(adTaskSchedulerDTO1).isEqualTo(adTaskSchedulerDTO2);
        adTaskSchedulerDTO2.setId(2L);
        assertThat(adTaskSchedulerDTO1).isNotEqualTo(adTaskSchedulerDTO2);
        adTaskSchedulerDTO1.setId(null);
        assertThat(adTaskSchedulerDTO1).isNotEqualTo(adTaskSchedulerDTO2);
    }
}
