package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvalMethodSubCriteriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvalMethodSubCriteriaDTO.class);
        CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO1 = new CEvalMethodSubCriteriaDTO();
        cEvalMethodSubCriteriaDTO1.setId(1L);
        CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO2 = new CEvalMethodSubCriteriaDTO();
        assertThat(cEvalMethodSubCriteriaDTO1).isNotEqualTo(cEvalMethodSubCriteriaDTO2);
        cEvalMethodSubCriteriaDTO2.setId(cEvalMethodSubCriteriaDTO1.getId());
        assertThat(cEvalMethodSubCriteriaDTO1).isEqualTo(cEvalMethodSubCriteriaDTO2);
        cEvalMethodSubCriteriaDTO2.setId(2L);
        assertThat(cEvalMethodSubCriteriaDTO1).isNotEqualTo(cEvalMethodSubCriteriaDTO2);
        cEvalMethodSubCriteriaDTO1.setId(null);
        assertThat(cEvalMethodSubCriteriaDTO1).isNotEqualTo(cEvalMethodSubCriteriaDTO2);
    }
}
