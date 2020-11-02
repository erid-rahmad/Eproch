package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CNonBusinessDayDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CNonBusinessDayDTO.class);
        CNonBusinessDayDTO cNonBusinessDayDTO1 = new CNonBusinessDayDTO();
        cNonBusinessDayDTO1.setId(1L);
        CNonBusinessDayDTO cNonBusinessDayDTO2 = new CNonBusinessDayDTO();
        assertThat(cNonBusinessDayDTO1).isNotEqualTo(cNonBusinessDayDTO2);
        cNonBusinessDayDTO2.setId(cNonBusinessDayDTO1.getId());
        assertThat(cNonBusinessDayDTO1).isEqualTo(cNonBusinessDayDTO2);
        cNonBusinessDayDTO2.setId(2L);
        assertThat(cNonBusinessDayDTO1).isNotEqualTo(cNonBusinessDayDTO2);
        cNonBusinessDayDTO1.setId(null);
        assertThat(cNonBusinessDayDTO1).isNotEqualTo(cNonBusinessDayDTO2);
    }
}
