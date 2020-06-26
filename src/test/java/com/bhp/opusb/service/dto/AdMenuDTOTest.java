package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdMenuDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdMenuDTO.class);
        AdMenuDTO adMenuDTO1 = new AdMenuDTO();
        adMenuDTO1.setId(1L);
        AdMenuDTO adMenuDTO2 = new AdMenuDTO();
        assertThat(adMenuDTO1).isNotEqualTo(adMenuDTO2);
        adMenuDTO2.setId(adMenuDTO1.getId());
        assertThat(adMenuDTO1).isEqualTo(adMenuDTO2);
        adMenuDTO2.setId(2L);
        assertThat(adMenuDTO1).isNotEqualTo(adMenuDTO2);
        adMenuDTO1.setId(null);
        assertThat(adMenuDTO1).isNotEqualTo(adMenuDTO2);
    }
}
