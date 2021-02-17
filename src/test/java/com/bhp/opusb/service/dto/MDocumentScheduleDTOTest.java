package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MDocumentScheduleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MDocumentScheduleDTO.class);
        MDocumentScheduleDTO mDocumentScheduleDTO1 = new MDocumentScheduleDTO();
        mDocumentScheduleDTO1.setId(1L);
        MDocumentScheduleDTO mDocumentScheduleDTO2 = new MDocumentScheduleDTO();
        assertThat(mDocumentScheduleDTO1).isNotEqualTo(mDocumentScheduleDTO2);
        mDocumentScheduleDTO2.setId(mDocumentScheduleDTO1.getId());
        assertThat(mDocumentScheduleDTO1).isEqualTo(mDocumentScheduleDTO2);
        mDocumentScheduleDTO2.setId(2L);
        assertThat(mDocumentScheduleDTO1).isNotEqualTo(mDocumentScheduleDTO2);
        mDocumentScheduleDTO1.setId(null);
        assertThat(mDocumentScheduleDTO1).isNotEqualTo(mDocumentScheduleDTO2);
    }
}
