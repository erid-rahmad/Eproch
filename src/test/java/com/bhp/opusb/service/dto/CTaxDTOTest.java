package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CTaxDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CTaxDTO.class);
        CTaxDTO cTaxDTO1 = new CTaxDTO();
        cTaxDTO1.setId(1L);
        CTaxDTO cTaxDTO2 = new CTaxDTO();
        assertThat(cTaxDTO1).isNotEqualTo(cTaxDTO2);
        cTaxDTO2.setId(cTaxDTO1.getId());
        assertThat(cTaxDTO1).isEqualTo(cTaxDTO2);
        cTaxDTO2.setId(2L);
        assertThat(cTaxDTO1).isNotEqualTo(cTaxDTO2);
        cTaxDTO1.setId(null);
        assertThat(cTaxDTO1).isNotEqualTo(cTaxDTO2);
    }
}
