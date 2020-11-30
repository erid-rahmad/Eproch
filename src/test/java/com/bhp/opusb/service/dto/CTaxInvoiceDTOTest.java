package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CTaxInvoiceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CTaxInvoiceDTO.class);
        CTaxInvoiceDTO cTaxInvoiceDTO1 = new CTaxInvoiceDTO();
        cTaxInvoiceDTO1.setId(1L);
        CTaxInvoiceDTO cTaxInvoiceDTO2 = new CTaxInvoiceDTO();
        assertThat(cTaxInvoiceDTO1).isNotEqualTo(cTaxInvoiceDTO2);
        cTaxInvoiceDTO2.setId(cTaxInvoiceDTO1.getId());
        assertThat(cTaxInvoiceDTO1).isEqualTo(cTaxInvoiceDTO2);
        cTaxInvoiceDTO2.setId(2L);
        assertThat(cTaxInvoiceDTO1).isNotEqualTo(cTaxInvoiceDTO2);
        cTaxInvoiceDTO1.setId(null);
        assertThat(cTaxInvoiceDTO1).isNotEqualTo(cTaxInvoiceDTO2);
    }
}
