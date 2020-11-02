package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorDTO.class);
        CVendorDTO cVendorDTO1 = new CVendorDTO();
        cVendorDTO1.setId(1L);
        CVendorDTO cVendorDTO2 = new CVendorDTO();
        assertThat(cVendorDTO1).isNotEqualTo(cVendorDTO2);
        cVendorDTO2.setId(cVendorDTO1.getId());
        assertThat(cVendorDTO1).isEqualTo(cVendorDTO2);
        cVendorDTO2.setId(2L);
        assertThat(cVendorDTO1).isNotEqualTo(cVendorDTO2);
        cVendorDTO1.setId(null);
        assertThat(cVendorDTO1).isNotEqualTo(cVendorDTO2);
    }
}
