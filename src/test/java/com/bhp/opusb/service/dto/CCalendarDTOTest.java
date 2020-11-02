package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CCalendarDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCalendarDTO.class);
        CCalendarDTO cCalendarDTO1 = new CCalendarDTO();
        cCalendarDTO1.setId(1L);
        CCalendarDTO cCalendarDTO2 = new CCalendarDTO();
        assertThat(cCalendarDTO1).isNotEqualTo(cCalendarDTO2);
        cCalendarDTO2.setId(cCalendarDTO1.getId());
        assertThat(cCalendarDTO1).isEqualTo(cCalendarDTO2);
        cCalendarDTO2.setId(2L);
        assertThat(cCalendarDTO1).isNotEqualTo(cCalendarDTO2);
        cCalendarDTO1.setId(null);
        assertThat(cCalendarDTO1).isNotEqualTo(cCalendarDTO2);
    }
}
