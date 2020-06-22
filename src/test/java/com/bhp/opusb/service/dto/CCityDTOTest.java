package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CCityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCityDTO.class);
        CCityDTO cCityDTO1 = new CCityDTO();
        cCityDTO1.setId(1L);
        CCityDTO cCityDTO2 = new CCityDTO();
        assertThat(cCityDTO1).isNotEqualTo(cCityDTO2);
        cCityDTO2.setId(cCityDTO1.getId());
        assertThat(cCityDTO1).isEqualTo(cCityDTO2);
        cCityDTO2.setId(2L);
        assertThat(cCityDTO1).isNotEqualTo(cCityDTO2);
        cCityDTO1.setId(null);
        assertThat(cCityDTO1).isNotEqualTo(cCityDTO2);
    }
}
