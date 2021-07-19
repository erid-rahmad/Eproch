package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalRegistrationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalRegistrationDTO.class);
        MPrequalRegistrationDTO mPrequalRegistrationDTO1 = new MPrequalRegistrationDTO();
        mPrequalRegistrationDTO1.setId(1L);
        MPrequalRegistrationDTO mPrequalRegistrationDTO2 = new MPrequalRegistrationDTO();
        assertThat(mPrequalRegistrationDTO1).isNotEqualTo(mPrequalRegistrationDTO2);
        mPrequalRegistrationDTO2.setId(mPrequalRegistrationDTO1.getId());
        assertThat(mPrequalRegistrationDTO1).isEqualTo(mPrequalRegistrationDTO2);
        mPrequalRegistrationDTO2.setId(2L);
        assertThat(mPrequalRegistrationDTO1).isNotEqualTo(mPrequalRegistrationDTO2);
        mPrequalRegistrationDTO1.setId(null);
        assertThat(mPrequalRegistrationDTO1).isNotEqualTo(mPrequalRegistrationDTO2);
    }
}
