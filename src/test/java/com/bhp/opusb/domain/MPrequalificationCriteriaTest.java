package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationCriteriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationCriteria.class);
        MPrequalificationCriteria mPrequalificationCriteria1 = new MPrequalificationCriteria();
        mPrequalificationCriteria1.setId(1L);
        MPrequalificationCriteria mPrequalificationCriteria2 = new MPrequalificationCriteria();
        mPrequalificationCriteria2.setId(mPrequalificationCriteria1.getId());
        assertThat(mPrequalificationCriteria1).isEqualTo(mPrequalificationCriteria2);
        mPrequalificationCriteria2.setId(2L);
        assertThat(mPrequalificationCriteria1).isNotEqualTo(mPrequalificationCriteria2);
        mPrequalificationCriteria1.setId(null);
        assertThat(mPrequalificationCriteria1).isNotEqualTo(mPrequalificationCriteria2);
    }
}
