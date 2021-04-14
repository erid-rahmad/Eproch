package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEventDTO.class);
        CEventDTO cEventDTO1 = new CEventDTO();
        cEventDTO1.setId(1L);
        CEventDTO cEventDTO2 = new CEventDTO();
        assertThat(cEventDTO1).isNotEqualTo(cEventDTO2);
        cEventDTO2.setId(cEventDTO1.getId());
        assertThat(cEventDTO1).isEqualTo(cEventDTO2);
        cEventDTO2.setId(2L);
        assertThat(cEventDTO1).isNotEqualTo(cEventDTO2);
        cEventDTO1.setId(null);
        assertThat(cEventDTO1).isNotEqualTo(cEventDTO2);
    }
}
