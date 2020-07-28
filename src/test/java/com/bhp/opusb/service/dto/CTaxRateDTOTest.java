package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CTaxRateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CTaxRateDTO.class);
        CTaxRateDTO cTaxRateDTO1 = new CTaxRateDTO();
        cTaxRateDTO1.setId(1L);
        CTaxRateDTO cTaxRateDTO2 = new CTaxRateDTO();
        assertThat(cTaxRateDTO1).isNotEqualTo(cTaxRateDTO2);
        cTaxRateDTO2.setId(cTaxRateDTO1.getId());
        assertThat(cTaxRateDTO1).isEqualTo(cTaxRateDTO2);
        cTaxRateDTO2.setId(2L);
        assertThat(cTaxRateDTO1).isNotEqualTo(cTaxRateDTO2);
        cTaxRateDTO1.setId(null);
        assertThat(cTaxRateDTO1).isNotEqualTo(cTaxRateDTO2);
    }
}
