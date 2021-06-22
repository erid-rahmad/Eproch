package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorEvaluationLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorEvaluationLineDTO.class);
        MVendorEvaluationLineDTO mVendorEvaluationLineDTO1 = new MVendorEvaluationLineDTO();
        mVendorEvaluationLineDTO1.setId(1L);
        MVendorEvaluationLineDTO mVendorEvaluationLineDTO2 = new MVendorEvaluationLineDTO();
        assertThat(mVendorEvaluationLineDTO1).isNotEqualTo(mVendorEvaluationLineDTO2);
        mVendorEvaluationLineDTO2.setId(mVendorEvaluationLineDTO1.getId());
        assertThat(mVendorEvaluationLineDTO1).isEqualTo(mVendorEvaluationLineDTO2);
        mVendorEvaluationLineDTO2.setId(2L);
        assertThat(mVendorEvaluationLineDTO1).isNotEqualTo(mVendorEvaluationLineDTO2);
        mVendorEvaluationLineDTO1.setId(null);
        assertThat(mVendorEvaluationLineDTO1).isNotEqualTo(mVendorEvaluationLineDTO2);
    }
}
