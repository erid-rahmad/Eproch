package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorScoringCriteriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorScoringCriteria.class);
        MVendorScoringCriteria mVendorScoringCriteria1 = new MVendorScoringCriteria();
        mVendorScoringCriteria1.setId(1L);
        MVendorScoringCriteria mVendorScoringCriteria2 = new MVendorScoringCriteria();
        mVendorScoringCriteria2.setId(mVendorScoringCriteria1.getId());
        assertThat(mVendorScoringCriteria1).isEqualTo(mVendorScoringCriteria2);
        mVendorScoringCriteria2.setId(2L);
        assertThat(mVendorScoringCriteria1).isNotEqualTo(mVendorScoringCriteria2);
        mVendorScoringCriteria1.setId(null);
        assertThat(mVendorScoringCriteria1).isNotEqualTo(mVendorScoringCriteria2);
    }
}
