package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CTaskDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CTaskDTO.class);
        CTaskDTO cTaskDTO1 = new CTaskDTO();
        cTaskDTO1.setId(1L);
        CTaskDTO cTaskDTO2 = new CTaskDTO();
        assertThat(cTaskDTO1).isNotEqualTo(cTaskDTO2);
        cTaskDTO2.setId(cTaskDTO1.getId());
        assertThat(cTaskDTO1).isEqualTo(cTaskDTO2);
        cTaskDTO2.setId(2L);
        assertThat(cTaskDTO1).isNotEqualTo(cTaskDTO2);
        cTaskDTO1.setId(null);
        assertThat(cTaskDTO1).isNotEqualTo(cTaskDTO2);
    }
}
