package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdCalloutDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdCalloutDTO.class);
        AdCalloutDTO adCalloutDTO1 = new AdCalloutDTO();
        adCalloutDTO1.setId(1L);
        AdCalloutDTO adCalloutDTO2 = new AdCalloutDTO();
        assertThat(adCalloutDTO1).isNotEqualTo(adCalloutDTO2);
        adCalloutDTO2.setId(adCalloutDTO1.getId());
        assertThat(adCalloutDTO1).isEqualTo(adCalloutDTO2);
        adCalloutDTO2.setId(2L);
        assertThat(adCalloutDTO1).isNotEqualTo(adCalloutDTO2);
        adCalloutDTO1.setId(null);
        assertThat(adCalloutDTO1).isNotEqualTo(adCalloutDTO2);
    }
}
