package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalificationEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalificationEventDTO.class);
        CPrequalificationEventDTO cPrequalificationEventDTO1 = new CPrequalificationEventDTO();
        cPrequalificationEventDTO1.setId(1L);
        CPrequalificationEventDTO cPrequalificationEventDTO2 = new CPrequalificationEventDTO();
        assertThat(cPrequalificationEventDTO1).isNotEqualTo(cPrequalificationEventDTO2);
        cPrequalificationEventDTO2.setId(cPrequalificationEventDTO1.getId());
        assertThat(cPrequalificationEventDTO1).isEqualTo(cPrequalificationEventDTO2);
        cPrequalificationEventDTO2.setId(2L);
        assertThat(cPrequalificationEventDTO1).isNotEqualTo(cPrequalificationEventDTO2);
        cPrequalificationEventDTO1.setId(null);
        assertThat(cPrequalificationEventDTO1).isNotEqualTo(cPrequalificationEventDTO2);
    }
}
