package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorTaxTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorTax.class);
        CVendorTax cVendorTax1 = new CVendorTax();
        cVendorTax1.setId(1L);
        CVendorTax cVendorTax2 = new CVendorTax();
        cVendorTax2.setId(cVendorTax1.getId());
        assertThat(cVendorTax1).isEqualTo(cVendorTax2);
        cVendorTax2.setId(2L);
        assertThat(cVendorTax1).isNotEqualTo(cVendorTax2);
        cVendorTax1.setId(null);
        assertThat(cVendorTax1).isNotEqualTo(cVendorTax2);
    }
}
