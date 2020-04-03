package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class PersonInChargeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonInChargeDTO.class);
        PersonInChargeDTO personInChargeDTO1 = new PersonInChargeDTO();
        personInChargeDTO1.setId(1L);
        PersonInChargeDTO personInChargeDTO2 = new PersonInChargeDTO();
        assertThat(personInChargeDTO1).isNotEqualTo(personInChargeDTO2);
        personInChargeDTO2.setId(personInChargeDTO1.getId());
        assertThat(personInChargeDTO1).isEqualTo(personInChargeDTO2);
        personInChargeDTO2.setId(2L);
        assertThat(personInChargeDTO1).isNotEqualTo(personInChargeDTO2);
        personInChargeDTO1.setId(null);
        assertThat(personInChargeDTO1).isNotEqualTo(personInChargeDTO2);
    }
}
