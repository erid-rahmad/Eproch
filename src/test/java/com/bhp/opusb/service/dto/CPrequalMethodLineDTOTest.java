package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalMethodLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalMethodLineDTO.class);
        CPrequalMethodLineDTO cPrequalMethodLineDTO1 = new CPrequalMethodLineDTO();
        cPrequalMethodLineDTO1.setId(1L);
        CPrequalMethodLineDTO cPrequalMethodLineDTO2 = new CPrequalMethodLineDTO();
        assertThat(cPrequalMethodLineDTO1).isNotEqualTo(cPrequalMethodLineDTO2);
        cPrequalMethodLineDTO2.setId(cPrequalMethodLineDTO1.getId());
        assertThat(cPrequalMethodLineDTO1).isEqualTo(cPrequalMethodLineDTO2);
        cPrequalMethodLineDTO2.setId(2L);
        assertThat(cPrequalMethodLineDTO1).isNotEqualTo(cPrequalMethodLineDTO2);
        cPrequalMethodLineDTO1.setId(null);
        assertThat(cPrequalMethodLineDTO1).isNotEqualTo(cPrequalMethodLineDTO2);
    }
}
