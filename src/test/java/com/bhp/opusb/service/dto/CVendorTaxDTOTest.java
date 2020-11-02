package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorTaxDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorTaxDTO.class);
        CVendorTaxDTO cVendorTaxDTO1 = new CVendorTaxDTO();
        cVendorTaxDTO1.setId(1L);
        CVendorTaxDTO cVendorTaxDTO2 = new CVendorTaxDTO();
        assertThat(cVendorTaxDTO1).isNotEqualTo(cVendorTaxDTO2);
        cVendorTaxDTO2.setId(cVendorTaxDTO1.getId());
        assertThat(cVendorTaxDTO1).isEqualTo(cVendorTaxDTO2);
        cVendorTaxDTO2.setId(2L);
        assertThat(cVendorTaxDTO1).isNotEqualTo(cVendorTaxDTO2);
        cVendorTaxDTO1.setId(null);
        assertThat(cVendorTaxDTO1).isNotEqualTo(cVendorTaxDTO2);
    }
}
