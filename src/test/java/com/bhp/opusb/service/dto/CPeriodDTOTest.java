package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPeriodDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPeriodDTO.class);
        CPeriodDTO cPeriodDTO1 = new CPeriodDTO();
        cPeriodDTO1.setId(1L);
        CPeriodDTO cPeriodDTO2 = new CPeriodDTO();
        assertThat(cPeriodDTO1).isNotEqualTo(cPeriodDTO2);
        cPeriodDTO2.setId(cPeriodDTO1.getId());
        assertThat(cPeriodDTO1).isEqualTo(cPeriodDTO2);
        cPeriodDTO2.setId(2L);
        assertThat(cPeriodDTO1).isNotEqualTo(cPeriodDTO2);
        cPeriodDTO1.setId(null);
        assertThat(cPeriodDTO1).isNotEqualTo(cPeriodDTO2);
    }
}
