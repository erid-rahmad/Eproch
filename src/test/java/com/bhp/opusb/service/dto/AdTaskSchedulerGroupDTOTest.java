package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTaskSchedulerGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTaskSchedulerGroupDTO.class);
        AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO1 = new AdTaskSchedulerGroupDTO();
        adTaskSchedulerGroupDTO1.setId(1L);
        AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO2 = new AdTaskSchedulerGroupDTO();
        assertThat(adTaskSchedulerGroupDTO1).isNotEqualTo(adTaskSchedulerGroupDTO2);
        adTaskSchedulerGroupDTO2.setId(adTaskSchedulerGroupDTO1.getId());
        assertThat(adTaskSchedulerGroupDTO1).isEqualTo(adTaskSchedulerGroupDTO2);
        adTaskSchedulerGroupDTO2.setId(2L);
        assertThat(adTaskSchedulerGroupDTO1).isNotEqualTo(adTaskSchedulerGroupDTO2);
        adTaskSchedulerGroupDTO1.setId(null);
        assertThat(adTaskSchedulerGroupDTO1).isNotEqualTo(adTaskSchedulerGroupDTO2);
    }
}
