package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPersonInChargeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPersonInChargeDTO.class);
        CPersonInChargeDTO cPersonInChargeDTO1 = new CPersonInChargeDTO();
        cPersonInChargeDTO1.setId(1L);
        CPersonInChargeDTO cPersonInChargeDTO2 = new CPersonInChargeDTO();
        assertThat(cPersonInChargeDTO1).isNotEqualTo(cPersonInChargeDTO2);
        cPersonInChargeDTO2.setId(cPersonInChargeDTO1.getId());
        assertThat(cPersonInChargeDTO1).isEqualTo(cPersonInChargeDTO2);
        cPersonInChargeDTO2.setId(2L);
        assertThat(cPersonInChargeDTO1).isNotEqualTo(cPersonInChargeDTO2);
        cPersonInChargeDTO1.setId(null);
        assertThat(cPersonInChargeDTO1).isNotEqualTo(cPersonInChargeDTO2);
    }
}
