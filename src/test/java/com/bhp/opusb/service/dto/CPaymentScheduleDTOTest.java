package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPaymentScheduleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPaymentScheduleDTO.class);
        CPaymentScheduleDTO cPaymentScheduleDTO1 = new CPaymentScheduleDTO();
        cPaymentScheduleDTO1.setId(1L);
        CPaymentScheduleDTO cPaymentScheduleDTO2 = new CPaymentScheduleDTO();
        assertThat(cPaymentScheduleDTO1).isNotEqualTo(cPaymentScheduleDTO2);
        cPaymentScheduleDTO2.setId(cPaymentScheduleDTO1.getId());
        assertThat(cPaymentScheduleDTO1).isEqualTo(cPaymentScheduleDTO2);
        cPaymentScheduleDTO2.setId(2L);
        assertThat(cPaymentScheduleDTO1).isNotEqualTo(cPaymentScheduleDTO2);
        cPaymentScheduleDTO1.setId(null);
        assertThat(cPaymentScheduleDTO1).isNotEqualTo(cPaymentScheduleDTO2);
    }
}
