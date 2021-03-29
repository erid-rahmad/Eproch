package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorEvaluationLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorEvaluationLineDTO.class);
        CVendorEvaluationLineDTO cVendorEvaluationLineDTO1 = new CVendorEvaluationLineDTO();
        cVendorEvaluationLineDTO1.setId(1L);
        CVendorEvaluationLineDTO cVendorEvaluationLineDTO2 = new CVendorEvaluationLineDTO();
        assertThat(cVendorEvaluationLineDTO1).isNotEqualTo(cVendorEvaluationLineDTO2);
        cVendorEvaluationLineDTO2.setId(cVendorEvaluationLineDTO1.getId());
        assertThat(cVendorEvaluationLineDTO1).isEqualTo(cVendorEvaluationLineDTO2);
        cVendorEvaluationLineDTO2.setId(2L);
        assertThat(cVendorEvaluationLineDTO1).isNotEqualTo(cVendorEvaluationLineDTO2);
        cVendorEvaluationLineDTO1.setId(null);
        assertThat(cVendorEvaluationLineDTO1).isNotEqualTo(cVendorEvaluationLineDTO2);
    }
}
