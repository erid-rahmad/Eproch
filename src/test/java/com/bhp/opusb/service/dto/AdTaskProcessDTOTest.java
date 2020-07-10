package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdTaskProcessDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdTaskProcessDTO.class);
        AdTaskProcessDTO adTaskProcessDTO1 = new AdTaskProcessDTO();
        adTaskProcessDTO1.setId(1L);
        AdTaskProcessDTO adTaskProcessDTO2 = new AdTaskProcessDTO();
        assertThat(adTaskProcessDTO1).isNotEqualTo(adTaskProcessDTO2);
        adTaskProcessDTO2.setId(adTaskProcessDTO1.getId());
        assertThat(adTaskProcessDTO1).isEqualTo(adTaskProcessDTO2);
        adTaskProcessDTO2.setId(2L);
        assertThat(adTaskProcessDTO1).isNotEqualTo(adTaskProcessDTO2);
        adTaskProcessDTO1.setId(null);
        assertThat(adTaskProcessDTO1).isNotEqualTo(adTaskProcessDTO2);
    }
}
