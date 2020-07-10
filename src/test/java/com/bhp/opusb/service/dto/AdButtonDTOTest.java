package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdButtonDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdButtonDTO.class);
        AdButtonDTO adButtonDTO1 = new AdButtonDTO();
        adButtonDTO1.setId(1L);
        AdButtonDTO adButtonDTO2 = new AdButtonDTO();
        assertThat(adButtonDTO1).isNotEqualTo(adButtonDTO2);
        adButtonDTO2.setId(adButtonDTO1.getId());
        assertThat(adButtonDTO1).isEqualTo(adButtonDTO2);
        adButtonDTO2.setId(2L);
        assertThat(adButtonDTO1).isNotEqualTo(adButtonDTO2);
        adButtonDTO1.setId(null);
        assertThat(adButtonDTO1).isNotEqualTo(adButtonDTO2);
    }
}
