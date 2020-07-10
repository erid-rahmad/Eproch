package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTriggerParamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTriggerParamDTO.class);
        AdTriggerParamDTO adTriggerParamDTO1 = new AdTriggerParamDTO();
        adTriggerParamDTO1.setId(1L);
        AdTriggerParamDTO adTriggerParamDTO2 = new AdTriggerParamDTO();
        assertThat(adTriggerParamDTO1).isNotEqualTo(adTriggerParamDTO2);
        adTriggerParamDTO2.setId(adTriggerParamDTO1.getId());
        assertThat(adTriggerParamDTO1).isEqualTo(adTriggerParamDTO2);
        adTriggerParamDTO2.setId(2L);
        assertThat(adTriggerParamDTO1).isNotEqualTo(adTriggerParamDTO2);
        adTriggerParamDTO1.setId(null);
        assertThat(adTriggerParamDTO1).isNotEqualTo(adTriggerParamDTO2);
    }
}
