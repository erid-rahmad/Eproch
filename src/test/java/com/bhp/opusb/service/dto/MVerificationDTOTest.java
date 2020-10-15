package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVerificationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVerificationDTO.class);
        MVerificationDTO mVerificationDTO1 = new MVerificationDTO();
        mVerificationDTO1.setId(1L);
        MVerificationDTO mVerificationDTO2 = new MVerificationDTO();
        assertThat(mVerificationDTO1).isNotEqualTo(mVerificationDTO2);
        mVerificationDTO2.setId(mVerificationDTO1.getId());
        assertThat(mVerificationDTO1).isEqualTo(mVerificationDTO2);
        mVerificationDTO2.setId(2L);
        assertThat(mVerificationDTO1).isNotEqualTo(mVerificationDTO2);
        mVerificationDTO1.setId(null);
        assertThat(mVerificationDTO1).isNotEqualTo(mVerificationDTO2);
    }
}
