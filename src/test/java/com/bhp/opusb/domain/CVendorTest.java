package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendor.class);
        CVendor cVendor1 = new CVendor();
        cVendor1.setId(1L);
        CVendor cVendor2 = new CVendor();
        cVendor2.setId(cVendor1.getId());
        assertThat(cVendor1).isEqualTo(cVendor2);
        cVendor2.setId(2L);
        assertThat(cVendor1).isNotEqualTo(cVendor2);
        cVendor1.setId(null);
        assertThat(cVendor1).isNotEqualTo(cVendor2);
    }
}
