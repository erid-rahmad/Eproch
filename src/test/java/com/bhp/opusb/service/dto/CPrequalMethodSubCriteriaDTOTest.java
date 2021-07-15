package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalMethodSubCriteriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalMethodSubCriteriaDTO.class);
        CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO1 = new CPrequalMethodSubCriteriaDTO();
        cPrequalMethodSubCriteriaDTO1.setId(1L);
        CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO2 = new CPrequalMethodSubCriteriaDTO();
        assertThat(cPrequalMethodSubCriteriaDTO1).isNotEqualTo(cPrequalMethodSubCriteriaDTO2);
        cPrequalMethodSubCriteriaDTO2.setId(cPrequalMethodSubCriteriaDTO1.getId());
        assertThat(cPrequalMethodSubCriteriaDTO1).isEqualTo(cPrequalMethodSubCriteriaDTO2);
        cPrequalMethodSubCriteriaDTO2.setId(2L);
        assertThat(cPrequalMethodSubCriteriaDTO1).isNotEqualTo(cPrequalMethodSubCriteriaDTO2);
        cPrequalMethodSubCriteriaDTO1.setId(null);
        assertThat(cPrequalMethodSubCriteriaDTO1).isNotEqualTo(cPrequalMethodSubCriteriaDTO2);
    }
}
