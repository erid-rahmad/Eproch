package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTaskApplicationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTaskApplicationDTO.class);
        AdTaskApplicationDTO adTaskApplicationDTO1 = new AdTaskApplicationDTO();
        adTaskApplicationDTO1.setId(1L);
        AdTaskApplicationDTO adTaskApplicationDTO2 = new AdTaskApplicationDTO();
        assertThat(adTaskApplicationDTO1).isNotEqualTo(adTaskApplicationDTO2);
        adTaskApplicationDTO2.setId(adTaskApplicationDTO1.getId());
        assertThat(adTaskApplicationDTO1).isEqualTo(adTaskApplicationDTO2);
        adTaskApplicationDTO2.setId(2L);
        assertThat(adTaskApplicationDTO1).isNotEqualTo(adTaskApplicationDTO2);
        adTaskApplicationDTO1.setId(null);
        assertThat(adTaskApplicationDTO1).isNotEqualTo(adTaskApplicationDTO2);
    }
}
