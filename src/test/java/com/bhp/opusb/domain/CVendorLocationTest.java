package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorLocationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorLocation.class);
        CVendorLocation cVendorLocation1 = new CVendorLocation();
        cVendorLocation1.setId(1L);
        CVendorLocation cVendorLocation2 = new CVendorLocation();
        cVendorLocation2.setId(cVendorLocation1.getId());
        assertThat(cVendorLocation1).isEqualTo(cVendorLocation2);
        cVendorLocation2.setId(2L);
        assertThat(cVendorLocation1).isNotEqualTo(cVendorLocation2);
        cVendorLocation1.setId(null);
        assertThat(cVendorLocation1).isNotEqualTo(cVendorLocation2);
    }
}
