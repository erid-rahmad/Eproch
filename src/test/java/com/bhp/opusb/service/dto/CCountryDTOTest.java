package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CCountryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCountryDTO.class);
        CCountryDTO cCountryDTO1 = new CCountryDTO();
        cCountryDTO1.setId(1L);
        CCountryDTO cCountryDTO2 = new CCountryDTO();
        assertThat(cCountryDTO1).isNotEqualTo(cCountryDTO2);
        cCountryDTO2.setId(cCountryDTO1.getId());
        assertThat(cCountryDTO1).isEqualTo(cCountryDTO2);
        cCountryDTO2.setId(2L);
        assertThat(cCountryDTO1).isNotEqualTo(cCountryDTO2);
        cCountryDTO1.setId(null);
        assertThat(cCountryDTO1).isNotEqualTo(cCountryDTO2);
    }
}
