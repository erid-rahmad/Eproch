package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorScoringTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorScoring.class);
        MVendorScoring mVendorScoring1 = new MVendorScoring();
        mVendorScoring1.setId(1L);
        MVendorScoring mVendorScoring2 = new MVendorScoring();
        mVendorScoring2.setId(mVendorScoring1.getId());
        assertThat(mVendorScoring1).isEqualTo(mVendorScoring2);
        mVendorScoring2.setId(2L);
        assertThat(mVendorScoring1).isNotEqualTo(mVendorScoring2);
        mVendorScoring1.setId(null);
        assertThat(mVendorScoring1).isNotEqualTo(mVendorScoring2);
    }
}
