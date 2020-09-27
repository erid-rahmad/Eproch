package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdCalloutTargetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdCalloutTargetDTO.class);
        AdCalloutTargetDTO adCalloutTargetDTO1 = new AdCalloutTargetDTO();
        adCalloutTargetDTO1.setId(1L);
        AdCalloutTargetDTO adCalloutTargetDTO2 = new AdCalloutTargetDTO();
        assertThat(adCalloutTargetDTO1).isNotEqualTo(adCalloutTargetDTO2);
        adCalloutTargetDTO2.setId(adCalloutTargetDTO1.getId());
        assertThat(adCalloutTargetDTO1).isEqualTo(adCalloutTargetDTO2);
        adCalloutTargetDTO2.setId(2L);
        assertThat(adCalloutTargetDTO1).isNotEqualTo(adCalloutTargetDTO2);
        adCalloutTargetDTO1.setId(null);
        assertThat(adCalloutTargetDTO1).isNotEqualTo(adCalloutTargetDTO2);
    }
}
