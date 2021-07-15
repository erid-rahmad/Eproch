package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationInformationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationInformation.class);
        MPrequalificationInformation mPrequalificationInformation1 = new MPrequalificationInformation();
        mPrequalificationInformation1.setId(1L);
        MPrequalificationInformation mPrequalificationInformation2 = new MPrequalificationInformation();
        mPrequalificationInformation2.setId(mPrequalificationInformation1.getId());
        assertThat(mPrequalificationInformation1).isEqualTo(mPrequalificationInformation2);
        mPrequalificationInformation2.setId(2L);
        assertThat(mPrequalificationInformation1).isNotEqualTo(mPrequalificationInformation2);
        mPrequalificationInformation1.setId(null);
        assertThat(mPrequalificationInformation1).isNotEqualTo(mPrequalificationInformation2);
    }
}
