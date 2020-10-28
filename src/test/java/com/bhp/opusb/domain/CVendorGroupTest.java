package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorGroup.class);
        CVendorGroup cVendorGroup1 = new CVendorGroup();
        cVendorGroup1.setId(1L);
        CVendorGroup cVendorGroup2 = new CVendorGroup();
        cVendorGroup2.setId(cVendorGroup1.getId());
        assertThat(cVendorGroup1).isEqualTo(cVendorGroup2);
        cVendorGroup2.setId(2L);
        assertThat(cVendorGroup1).isNotEqualTo(cVendorGroup2);
        cVendorGroup1.setId(null);
        assertThat(cVendorGroup1).isNotEqualTo(cVendorGroup2);
    }
}
