package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorEvaluationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorEvaluationDTO.class);
        MVendorEvaluationDTO mVendorEvaluationDTO1 = new MVendorEvaluationDTO();
        mVendorEvaluationDTO1.setId(1L);
        MVendorEvaluationDTO mVendorEvaluationDTO2 = new MVendorEvaluationDTO();
        assertThat(mVendorEvaluationDTO1).isNotEqualTo(mVendorEvaluationDTO2);
        mVendorEvaluationDTO2.setId(mVendorEvaluationDTO1.getId());
        assertThat(mVendorEvaluationDTO1).isEqualTo(mVendorEvaluationDTO2);
        mVendorEvaluationDTO2.setId(2L);
        assertThat(mVendorEvaluationDTO1).isNotEqualTo(mVendorEvaluationDTO2);
        mVendorEvaluationDTO1.setId(null);
        assertThat(mVendorEvaluationDTO1).isNotEqualTo(mVendorEvaluationDTO2);
    }
}
