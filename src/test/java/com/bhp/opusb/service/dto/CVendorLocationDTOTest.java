package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorLocationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorLocationDTO.class);
        CVendorLocationDTO cVendorLocationDTO1 = new CVendorLocationDTO();
        cVendorLocationDTO1.setId(1L);
        CVendorLocationDTO cVendorLocationDTO2 = new CVendorLocationDTO();
        assertThat(cVendorLocationDTO1).isNotEqualTo(cVendorLocationDTO2);
        cVendorLocationDTO2.setId(cVendorLocationDTO1.getId());
        assertThat(cVendorLocationDTO1).isEqualTo(cVendorLocationDTO2);
        cVendorLocationDTO2.setId(2L);
        assertThat(cVendorLocationDTO1).isNotEqualTo(cVendorLocationDTO2);
        cVendorLocationDTO1.setId(null);
        assertThat(cVendorLocationDTO1).isNotEqualTo(cVendorLocationDTO2);
    }
}
