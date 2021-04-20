package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEvalMethodCriteriaLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvalMethodCriteriaLineDTO.class);
        CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO1 = new CEvalMethodCriteriaLineDTO();
        cEvalMethodCriteriaLineDTO1.setId(1L);
        CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO2 = new CEvalMethodCriteriaLineDTO();
        assertThat(cEvalMethodCriteriaLineDTO1).isNotEqualTo(cEvalMethodCriteriaLineDTO2);
        cEvalMethodCriteriaLineDTO2.setId(cEvalMethodCriteriaLineDTO1.getId());
        assertThat(cEvalMethodCriteriaLineDTO1).isEqualTo(cEvalMethodCriteriaLineDTO2);
        cEvalMethodCriteriaLineDTO2.setId(2L);
        assertThat(cEvalMethodCriteriaLineDTO1).isNotEqualTo(cEvalMethodCriteriaLineDTO2);
        cEvalMethodCriteriaLineDTO1.setId(null);
        assertThat(cEvalMethodCriteriaLineDTO1).isNotEqualTo(cEvalMethodCriteriaLineDTO2);
    }
}
