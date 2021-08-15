package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationResult.class);
        MPrequalificationResult mPrequalificationResult1 = new MPrequalificationResult();
        mPrequalificationResult1.setId(1L);
        MPrequalificationResult mPrequalificationResult2 = new MPrequalificationResult();
        mPrequalificationResult2.setId(mPrequalificationResult1.getId());
        assertThat(mPrequalificationResult1).isEqualTo(mPrequalificationResult2);
        mPrequalificationResult2.setId(2L);
        assertThat(mPrequalificationResult1).isNotEqualTo(mPrequalificationResult2);
        mPrequalificationResult1.setId(null);
        assertThat(mPrequalificationResult1).isNotEqualTo(mPrequalificationResult2);
    }
}
