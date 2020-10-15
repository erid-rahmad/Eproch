package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVerificationLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVerificationLineDTO.class);
        MVerificationLineDTO mVerificationLineDTO1 = new MVerificationLineDTO();
        mVerificationLineDTO1.setId(1L);
        MVerificationLineDTO mVerificationLineDTO2 = new MVerificationLineDTO();
        assertThat(mVerificationLineDTO1).isNotEqualTo(mVerificationLineDTO2);
        mVerificationLineDTO2.setId(mVerificationLineDTO1.getId());
        assertThat(mVerificationLineDTO1).isEqualTo(mVerificationLineDTO2);
        mVerificationLineDTO2.setId(2L);
        assertThat(mVerificationLineDTO1).isNotEqualTo(mVerificationLineDTO2);
        mVerificationLineDTO1.setId(null);
        assertThat(mVerificationLineDTO1).isNotEqualTo(mVerificationLineDTO2);
    }
}
