package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdFormDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdFormDTO.class);
        AdFormDTO adFormDTO1 = new AdFormDTO();
        adFormDTO1.setId(1L);
        AdFormDTO adFormDTO2 = new AdFormDTO();
        assertThat(adFormDTO1).isNotEqualTo(adFormDTO2);
        adFormDTO2.setId(adFormDTO1.getId());
        assertThat(adFormDTO1).isEqualTo(adFormDTO2);
        adFormDTO2.setId(2L);
        assertThat(adFormDTO1).isNotEqualTo(adFormDTO2);
        adFormDTO1.setId(null);
        assertThat(adFormDTO1).isNotEqualTo(adFormDTO2);
    }
}
