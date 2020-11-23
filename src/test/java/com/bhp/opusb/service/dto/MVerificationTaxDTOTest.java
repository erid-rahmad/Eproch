package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVerificationTaxDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVerificationTaxDTO.class);
        MVerificationTaxDTO mVerificationTaxDTO1 = new MVerificationTaxDTO();
        mVerificationTaxDTO1.setId(1L);
        MVerificationTaxDTO mVerificationTaxDTO2 = new MVerificationTaxDTO();
        assertThat(mVerificationTaxDTO1).isNotEqualTo(mVerificationTaxDTO2);
        mVerificationTaxDTO2.setId(mVerificationTaxDTO1.getId());
        assertThat(mVerificationTaxDTO1).isEqualTo(mVerificationTaxDTO2);
        mVerificationTaxDTO2.setId(2L);
        assertThat(mVerificationTaxDTO1).isNotEqualTo(mVerificationTaxDTO2);
        mVerificationTaxDTO1.setId(null);
        assertThat(mVerificationTaxDTO1).isNotEqualTo(mVerificationTaxDTO2);
    }
}
