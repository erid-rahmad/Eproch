package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPreqRegistEvaluationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPreqRegistEvaluationDTO.class);
        MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO1 = new MPreqRegistEvaluationDTO();
        mPreqRegistEvaluationDTO1.setId(1L);
        MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO2 = new MPreqRegistEvaluationDTO();
        assertThat(mPreqRegistEvaluationDTO1).isNotEqualTo(mPreqRegistEvaluationDTO2);
        mPreqRegistEvaluationDTO2.setId(mPreqRegistEvaluationDTO1.getId());
        assertThat(mPreqRegistEvaluationDTO1).isEqualTo(mPreqRegistEvaluationDTO2);
        mPreqRegistEvaluationDTO2.setId(2L);
        assertThat(mPreqRegistEvaluationDTO1).isNotEqualTo(mPreqRegistEvaluationDTO2);
        mPreqRegistEvaluationDTO1.setId(null);
        assertThat(mPreqRegistEvaluationDTO1).isNotEqualTo(mPreqRegistEvaluationDTO2);
    }
}
