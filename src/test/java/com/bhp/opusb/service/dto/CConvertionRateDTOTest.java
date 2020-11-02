package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CConvertionRateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CConvertionRateDTO.class);
        CConvertionRateDTO cConvertionRateDTO1 = new CConvertionRateDTO();
        cConvertionRateDTO1.setId(1L);
        CConvertionRateDTO cConvertionRateDTO2 = new CConvertionRateDTO();
        assertThat(cConvertionRateDTO1).isNotEqualTo(cConvertionRateDTO2);
        cConvertionRateDTO2.setId(cConvertionRateDTO1.getId());
        assertThat(cConvertionRateDTO1).isEqualTo(cConvertionRateDTO2);
        cConvertionRateDTO2.setId(2L);
        assertThat(cConvertionRateDTO1).isNotEqualTo(cConvertionRateDTO2);
        cConvertionRateDTO1.setId(null);
        assertThat(cConvertionRateDTO1).isNotEqualTo(cConvertionRateDTO2);
    }
}
