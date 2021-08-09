package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationCriteriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationCriteriaDTO.class);
        MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO1 = new MPrequalificationCriteriaDTO();
        mPrequalificationCriteriaDTO1.setId(1L);
        MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO2 = new MPrequalificationCriteriaDTO();
        assertThat(mPrequalificationCriteriaDTO1).isNotEqualTo(mPrequalificationCriteriaDTO2);
        mPrequalificationCriteriaDTO2.setId(mPrequalificationCriteriaDTO1.getId());
        assertThat(mPrequalificationCriteriaDTO1).isEqualTo(mPrequalificationCriteriaDTO2);
        mPrequalificationCriteriaDTO2.setId(2L);
        assertThat(mPrequalificationCriteriaDTO1).isNotEqualTo(mPrequalificationCriteriaDTO2);
        mPrequalificationCriteriaDTO1.setId(null);
        assertThat(mPrequalificationCriteriaDTO1).isNotEqualTo(mPrequalificationCriteriaDTO2);
    }
}
