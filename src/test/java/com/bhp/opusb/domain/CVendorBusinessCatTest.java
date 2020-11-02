package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorBusinessCatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorBusinessCat.class);
        CVendorBusinessCat cVendorBusinessCat1 = new CVendorBusinessCat();
        cVendorBusinessCat1.setId(1L);
        CVendorBusinessCat cVendorBusinessCat2 = new CVendorBusinessCat();
        cVendorBusinessCat2.setId(cVendorBusinessCat1.getId());
        assertThat(cVendorBusinessCat1).isEqualTo(cVendorBusinessCat2);
        cVendorBusinessCat2.setId(2L);
        assertThat(cVendorBusinessCat1).isNotEqualTo(cVendorBusinessCat2);
        cVendorBusinessCat1.setId(null);
        assertThat(cVendorBusinessCat1).isNotEqualTo(cVendorBusinessCat2);
    }
}
