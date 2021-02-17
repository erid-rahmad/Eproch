package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEventTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEventTypeDTO.class);
        CEventTypeDTO cEventTypeDTO1 = new CEventTypeDTO();
        cEventTypeDTO1.setId(1L);
        CEventTypeDTO cEventTypeDTO2 = new CEventTypeDTO();
        assertThat(cEventTypeDTO1).isNotEqualTo(cEventTypeDTO2);
        cEventTypeDTO2.setId(cEventTypeDTO1.getId());
        assertThat(cEventTypeDTO1).isEqualTo(cEventTypeDTO2);
        cEventTypeDTO2.setId(2L);
        assertThat(cEventTypeDTO1).isNotEqualTo(cEventTypeDTO2);
        cEventTypeDTO1.setId(null);
        assertThat(cEventTypeDTO1).isNotEqualTo(cEventTypeDTO2);
    }
}
