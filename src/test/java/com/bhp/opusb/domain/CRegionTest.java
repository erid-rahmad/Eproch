package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CRegionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRegion.class);
        CRegion cRegion1 = new CRegion();
        cRegion1.setId(1L);
        CRegion cRegion2 = new CRegion();
        cRegion2.setId(cRegion1.getId());
        assertThat(cRegion1).isEqualTo(cRegion2);
        cRegion2.setId(2L);
        assertThat(cRegion1).isNotEqualTo(cRegion2);
        cRegion1.setId(null);
        assertThat(cRegion1).isNotEqualTo(cRegion2);
    }
}
