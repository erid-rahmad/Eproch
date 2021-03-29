package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorEvaluationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorEvaluationDTO.class);
        CVendorEvaluationDTO cVendorEvaluationDTO1 = new CVendorEvaluationDTO();
        cVendorEvaluationDTO1.setId(1L);
        CVendorEvaluationDTO cVendorEvaluationDTO2 = new CVendorEvaluationDTO();
        assertThat(cVendorEvaluationDTO1).isNotEqualTo(cVendorEvaluationDTO2);
        cVendorEvaluationDTO2.setId(cVendorEvaluationDTO1.getId());
        assertThat(cVendorEvaluationDTO1).isEqualTo(cVendorEvaluationDTO2);
        cVendorEvaluationDTO2.setId(2L);
        assertThat(cVendorEvaluationDTO1).isNotEqualTo(cVendorEvaluationDTO2);
        cVendorEvaluationDTO1.setId(null);
        assertThat(cVendorEvaluationDTO1).isNotEqualTo(cVendorEvaluationDTO2);
    }
}
