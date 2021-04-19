package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorScoringLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorScoringLineDTO.class);
        MVendorScoringLineDTO mVendorScoringLineDTO1 = new MVendorScoringLineDTO();
        mVendorScoringLineDTO1.setId(1L);
        MVendorScoringLineDTO mVendorScoringLineDTO2 = new MVendorScoringLineDTO();
        assertThat(mVendorScoringLineDTO1).isNotEqualTo(mVendorScoringLineDTO2);
        mVendorScoringLineDTO2.setId(mVendorScoringLineDTO1.getId());
        assertThat(mVendorScoringLineDTO1).isEqualTo(mVendorScoringLineDTO2);
        mVendorScoringLineDTO2.setId(2L);
        assertThat(mVendorScoringLineDTO1).isNotEqualTo(mVendorScoringLineDTO2);
        mVendorScoringLineDTO1.setId(null);
        assertThat(mVendorScoringLineDTO1).isNotEqualTo(mVendorScoringLineDTO2);
    }
}
