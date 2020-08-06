package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorBankAcctDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorBankAcctDTO.class);
        CVendorBankAcctDTO cVendorBankAcctDTO1 = new CVendorBankAcctDTO();
        cVendorBankAcctDTO1.setId(1L);
        CVendorBankAcctDTO cVendorBankAcctDTO2 = new CVendorBankAcctDTO();
        assertThat(cVendorBankAcctDTO1).isNotEqualTo(cVendorBankAcctDTO2);
        cVendorBankAcctDTO2.setId(cVendorBankAcctDTO1.getId());
        assertThat(cVendorBankAcctDTO1).isEqualTo(cVendorBankAcctDTO2);
        cVendorBankAcctDTO2.setId(2L);
        assertThat(cVendorBankAcctDTO1).isNotEqualTo(cVendorBankAcctDTO2);
        cVendorBankAcctDTO1.setId(null);
        assertThat(cVendorBankAcctDTO1).isNotEqualTo(cVendorBankAcctDTO2);
    }
}
