package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationEvalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationEval.class);
        MPrequalificationEval mPrequalificationEval1 = new MPrequalificationEval();
        mPrequalificationEval1.setId(1L);
        MPrequalificationEval mPrequalificationEval2 = new MPrequalificationEval();
        mPrequalificationEval2.setId(mPrequalificationEval1.getId());
        assertThat(mPrequalificationEval1).isEqualTo(mPrequalificationEval2);
        mPrequalificationEval2.setId(2L);
        assertThat(mPrequalificationEval1).isNotEqualTo(mPrequalificationEval2);
        mPrequalificationEval1.setId(null);
        assertThat(mPrequalificationEval1).isNotEqualTo(mPrequalificationEval2);
    }
}
