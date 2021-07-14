package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalMethodCriteriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalMethodCriteriaDTO.class);
        CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO1 = new CPrequalMethodCriteriaDTO();
        cPrequalMethodCriteriaDTO1.setId(1L);
        CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO2 = new CPrequalMethodCriteriaDTO();
        assertThat(cPrequalMethodCriteriaDTO1).isNotEqualTo(cPrequalMethodCriteriaDTO2);
        cPrequalMethodCriteriaDTO2.setId(cPrequalMethodCriteriaDTO1.getId());
        assertThat(cPrequalMethodCriteriaDTO1).isEqualTo(cPrequalMethodCriteriaDTO2);
        cPrequalMethodCriteriaDTO2.setId(2L);
        assertThat(cPrequalMethodCriteriaDTO1).isNotEqualTo(cPrequalMethodCriteriaDTO2);
        cPrequalMethodCriteriaDTO1.setId(null);
        assertThat(cPrequalMethodCriteriaDTO1).isNotEqualTo(cPrequalMethodCriteriaDTO2);
    }
}
