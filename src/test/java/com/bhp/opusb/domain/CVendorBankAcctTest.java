package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorBankAcctTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorBankAcct.class);
        CVendorBankAcct cVendorBankAcct1 = new CVendorBankAcct();
        cVendorBankAcct1.setId(1L);
        CVendorBankAcct cVendorBankAcct2 = new CVendorBankAcct();
        cVendorBankAcct2.setId(cVendorBankAcct1.getId());
        assertThat(cVendorBankAcct1).isEqualTo(cVendorBankAcct2);
        cVendorBankAcct2.setId(2L);
        assertThat(cVendorBankAcct1).isNotEqualTo(cVendorBankAcct2);
        cVendorBankAcct1.setId(null);
        assertThat(cVendorBankAcct1).isNotEqualTo(cVendorBankAcct2);
    }
}
