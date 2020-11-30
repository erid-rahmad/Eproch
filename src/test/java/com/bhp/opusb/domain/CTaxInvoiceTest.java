package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CTaxInvoiceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CTaxInvoice.class);
        CTaxInvoice cTaxInvoice1 = new CTaxInvoice();
        cTaxInvoice1.setId(1L);
        CTaxInvoice cTaxInvoice2 = new CTaxInvoice();
        cTaxInvoice2.setId(cTaxInvoice1.getId());
        assertThat(cTaxInvoice1).isEqualTo(cTaxInvoice2);
        cTaxInvoice2.setId(2L);
        assertThat(cTaxInvoice1).isNotEqualTo(cTaxInvoice2);
        cTaxInvoice1.setId(null);
        assertThat(cTaxInvoice1).isNotEqualTo(cTaxInvoice2);
    }
}
