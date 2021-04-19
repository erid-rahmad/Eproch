package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorScoringLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorScoringLine.class);
        MVendorScoringLine mVendorScoringLine1 = new MVendorScoringLine();
        mVendorScoringLine1.setId(1L);
        MVendorScoringLine mVendorScoringLine2 = new MVendorScoringLine();
        mVendorScoringLine2.setId(mVendorScoringLine1.getId());
        assertThat(mVendorScoringLine1).isEqualTo(mVendorScoringLine2);
        mVendorScoringLine2.setId(2L);
        assertThat(mVendorScoringLine1).isNotEqualTo(mVendorScoringLine2);
        mVendorScoringLine1.setId(null);
        assertThat(mVendorScoringLine1).isNotEqualTo(mVendorScoringLine2);
    }
}
