package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorScoringDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorScoringDTO.class);
        MVendorScoringDTO mVendorScoringDTO1 = new MVendorScoringDTO();
        mVendorScoringDTO1.setId(1L);
        MVendorScoringDTO mVendorScoringDTO2 = new MVendorScoringDTO();
        assertThat(mVendorScoringDTO1).isNotEqualTo(mVendorScoringDTO2);
        mVendorScoringDTO2.setId(mVendorScoringDTO1.getId());
        assertThat(mVendorScoringDTO1).isEqualTo(mVendorScoringDTO2);
        mVendorScoringDTO2.setId(2L);
        assertThat(mVendorScoringDTO1).isNotEqualTo(mVendorScoringDTO2);
        mVendorScoringDTO1.setId(null);
        assertThat(mVendorScoringDTO1).isNotEqualTo(mVendorScoringDTO2);
    }
}
