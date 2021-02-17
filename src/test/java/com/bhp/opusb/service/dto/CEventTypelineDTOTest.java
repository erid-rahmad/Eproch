package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEventTypelineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEventTypelineDTO.class);
        CEventTypelineDTO cEventTypelineDTO1 = new CEventTypelineDTO();
        cEventTypelineDTO1.setId(1L);
        CEventTypelineDTO cEventTypelineDTO2 = new CEventTypelineDTO();
        assertThat(cEventTypelineDTO1).isNotEqualTo(cEventTypelineDTO2);
        cEventTypelineDTO2.setId(cEventTypelineDTO1.getId());
        assertThat(cEventTypelineDTO1).isEqualTo(cEventTypelineDTO2);
        cEventTypelineDTO2.setId(2L);
        assertThat(cEventTypelineDTO1).isNotEqualTo(cEventTypelineDTO2);
        cEventTypelineDTO1.setId(null);
        assertThat(cEventTypelineDTO1).isNotEqualTo(cEventTypelineDTO2);
    }
}
