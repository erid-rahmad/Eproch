package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTriggerDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTriggerDTO.class);
        AdTriggerDTO adTriggerDTO1 = new AdTriggerDTO();
        adTriggerDTO1.setId(1L);
        AdTriggerDTO adTriggerDTO2 = new AdTriggerDTO();
        assertThat(adTriggerDTO1).isNotEqualTo(adTriggerDTO2);
        adTriggerDTO2.setId(adTriggerDTO1.getId());
        assertThat(adTriggerDTO1).isEqualTo(adTriggerDTO2);
        adTriggerDTO2.setId(2L);
        assertThat(adTriggerDTO1).isNotEqualTo(adTriggerDTO2);
        adTriggerDTO1.setId(null);
        assertThat(adTriggerDTO1).isNotEqualTo(adTriggerDTO2);
    }
}
