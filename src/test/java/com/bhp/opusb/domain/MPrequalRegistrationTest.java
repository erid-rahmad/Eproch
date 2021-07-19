package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalRegistrationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalRegistration.class);
        MPrequalRegistration mPrequalRegistration1 = new MPrequalRegistration();
        mPrequalRegistration1.setId(1L);
        MPrequalRegistration mPrequalRegistration2 = new MPrequalRegistration();
        mPrequalRegistration2.setId(mPrequalRegistration1.getId());
        assertThat(mPrequalRegistration1).isEqualTo(mPrequalRegistration2);
        mPrequalRegistration2.setId(2L);
        assertThat(mPrequalRegistration1).isNotEqualTo(mPrequalRegistration2);
        mPrequalRegistration1.setId(null);
        assertThat(mPrequalRegistration1).isNotEqualTo(mPrequalRegistration2);
    }
}
