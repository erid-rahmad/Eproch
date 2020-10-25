package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdUserAuthorityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdUserAuthorityDTO.class);
        AdUserAuthorityDTO adUserAuthorityDTO1 = new AdUserAuthorityDTO();
        adUserAuthorityDTO1.setId(1L);
        AdUserAuthorityDTO adUserAuthorityDTO2 = new AdUserAuthorityDTO();
        assertThat(adUserAuthorityDTO1).isNotEqualTo(adUserAuthorityDTO2);
        adUserAuthorityDTO2.setId(adUserAuthorityDTO1.getId());
        assertThat(adUserAuthorityDTO1).isEqualTo(adUserAuthorityDTO2);
        adUserAuthorityDTO2.setId(2L);
        assertThat(adUserAuthorityDTO1).isNotEqualTo(adUserAuthorityDTO2);
        adUserAuthorityDTO1.setId(null);
        assertThat(adUserAuthorityDTO1).isNotEqualTo(adUserAuthorityDTO2);
    }
}
