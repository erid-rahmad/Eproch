package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalificationStepDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalificationStepDTO.class);
        CPrequalificationStepDTO cPrequalificationStepDTO1 = new CPrequalificationStepDTO();
        cPrequalificationStepDTO1.setId(1L);
        CPrequalificationStepDTO cPrequalificationStepDTO2 = new CPrequalificationStepDTO();
        assertThat(cPrequalificationStepDTO1).isNotEqualTo(cPrequalificationStepDTO2);
        cPrequalificationStepDTO2.setId(cPrequalificationStepDTO1.getId());
        assertThat(cPrequalificationStepDTO1).isEqualTo(cPrequalificationStepDTO2);
        cPrequalificationStepDTO2.setId(2L);
        assertThat(cPrequalificationStepDTO1).isNotEqualTo(cPrequalificationStepDTO2);
        cPrequalificationStepDTO1.setId(null);
        assertThat(cPrequalificationStepDTO1).isNotEqualTo(cPrequalificationStepDTO2);
    }
}
