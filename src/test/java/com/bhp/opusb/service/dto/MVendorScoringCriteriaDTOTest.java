package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorScoringCriteriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorScoringCriteriaDTO.class);
        MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO1 = new MVendorScoringCriteriaDTO();
        mVendorScoringCriteriaDTO1.setId(1L);
        MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO2 = new MVendorScoringCriteriaDTO();
        assertThat(mVendorScoringCriteriaDTO1).isNotEqualTo(mVendorScoringCriteriaDTO2);
        mVendorScoringCriteriaDTO2.setId(mVendorScoringCriteriaDTO1.getId());
        assertThat(mVendorScoringCriteriaDTO1).isEqualTo(mVendorScoringCriteriaDTO2);
        mVendorScoringCriteriaDTO2.setId(2L);
        assertThat(mVendorScoringCriteriaDTO1).isNotEqualTo(mVendorScoringCriteriaDTO2);
        mVendorScoringCriteriaDTO1.setId(null);
        assertThat(mVendorScoringCriteriaDTO1).isNotEqualTo(mVendorScoringCriteriaDTO2);
    }
}
