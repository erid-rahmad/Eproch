package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationScheduleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationScheduleDTO.class);
        MPrequalificationScheduleDTO mPrequalificationScheduleDTO1 = new MPrequalificationScheduleDTO();
        mPrequalificationScheduleDTO1.setId(1L);
        MPrequalificationScheduleDTO mPrequalificationScheduleDTO2 = new MPrequalificationScheduleDTO();
        assertThat(mPrequalificationScheduleDTO1).isNotEqualTo(mPrequalificationScheduleDTO2);
        mPrequalificationScheduleDTO2.setId(mPrequalificationScheduleDTO1.getId());
        assertThat(mPrequalificationScheduleDTO1).isEqualTo(mPrequalificationScheduleDTO2);
        mPrequalificationScheduleDTO2.setId(2L);
        assertThat(mPrequalificationScheduleDTO1).isNotEqualTo(mPrequalificationScheduleDTO2);
        mPrequalificationScheduleDTO1.setId(null);
        assertThat(mPrequalificationScheduleDTO1).isNotEqualTo(mPrequalificationScheduleDTO2);
    }
}
