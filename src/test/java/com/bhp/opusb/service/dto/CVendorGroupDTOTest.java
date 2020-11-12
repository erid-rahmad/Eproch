package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorGroupDTO.class);
        CVendorGroupDTO cVendorGroupDTO1 = new CVendorGroupDTO();
        cVendorGroupDTO1.setId(1L);
        CVendorGroupDTO cVendorGroupDTO2 = new CVendorGroupDTO();
        assertThat(cVendorGroupDTO1).isNotEqualTo(cVendorGroupDTO2);
        cVendorGroupDTO2.setId(cVendorGroupDTO1.getId());
        assertThat(cVendorGroupDTO1).isEqualTo(cVendorGroupDTO2);
        cVendorGroupDTO2.setId(2L);
        assertThat(cVendorGroupDTO1).isNotEqualTo(cVendorGroupDTO2);
        cVendorGroupDTO1.setId(null);
        assertThat(cVendorGroupDTO1).isNotEqualTo(cVendorGroupDTO2);
    }
}
