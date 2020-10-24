package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdUserDTO.class);
        AdUserDTO adUserDTO1 = new AdUserDTO();
        adUserDTO1.setId(1L);
        AdUserDTO adUserDTO2 = new AdUserDTO();
        assertThat(adUserDTO1).isNotEqualTo(adUserDTO2);
        adUserDTO2.setId(adUserDTO1.getId());
        assertThat(adUserDTO1).isEqualTo(adUserDTO2);
        adUserDTO2.setId(2L);
        assertThat(adUserDTO1).isNotEqualTo(adUserDTO2);
        adUserDTO1.setId(null);
        assertThat(adUserDTO1).isNotEqualTo(adUserDTO2);
    }
}
