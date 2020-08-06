package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorBusinessCatDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorBusinessCatDTO.class);
        CVendorBusinessCatDTO cVendorBusinessCatDTO1 = new CVendorBusinessCatDTO();
        cVendorBusinessCatDTO1.setId(1L);
        CVendorBusinessCatDTO cVendorBusinessCatDTO2 = new CVendorBusinessCatDTO();
        assertThat(cVendorBusinessCatDTO1).isNotEqualTo(cVendorBusinessCatDTO2);
        cVendorBusinessCatDTO2.setId(cVendorBusinessCatDTO1.getId());
        assertThat(cVendorBusinessCatDTO1).isEqualTo(cVendorBusinessCatDTO2);
        cVendorBusinessCatDTO2.setId(2L);
        assertThat(cVendorBusinessCatDTO1).isNotEqualTo(cVendorBusinessCatDTO2);
        cVendorBusinessCatDTO1.setId(null);
        assertThat(cVendorBusinessCatDTO1).isNotEqualTo(cVendorBusinessCatDTO2);
    }
}
