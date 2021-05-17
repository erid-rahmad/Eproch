package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalificationEventLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalificationEventLineDTO.class);
        CPrequalificationEventLineDTO cPrequalificationEventLineDTO1 = new CPrequalificationEventLineDTO();
        cPrequalificationEventLineDTO1.setId(1L);
        CPrequalificationEventLineDTO cPrequalificationEventLineDTO2 = new CPrequalificationEventLineDTO();
        assertThat(cPrequalificationEventLineDTO1).isNotEqualTo(cPrequalificationEventLineDTO2);
        cPrequalificationEventLineDTO2.setId(cPrequalificationEventLineDTO1.getId());
        assertThat(cPrequalificationEventLineDTO1).isEqualTo(cPrequalificationEventLineDTO2);
        cPrequalificationEventLineDTO2.setId(2L);
        assertThat(cPrequalificationEventLineDTO1).isNotEqualTo(cPrequalificationEventLineDTO2);
        cPrequalificationEventLineDTO1.setId(null);
        assertThat(cPrequalificationEventLineDTO1).isNotEqualTo(cPrequalificationEventLineDTO2);
    }
}
