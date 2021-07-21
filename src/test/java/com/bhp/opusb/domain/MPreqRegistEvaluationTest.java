package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPreqRegistEvaluationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPreqRegistEvaluation.class);
        MPreqRegistEvaluation mPreqRegistEvaluation1 = new MPreqRegistEvaluation();
        mPreqRegistEvaluation1.setId(1L);
        MPreqRegistEvaluation mPreqRegistEvaluation2 = new MPreqRegistEvaluation();
        mPreqRegistEvaluation2.setId(mPreqRegistEvaluation1.getId());
        assertThat(mPreqRegistEvaluation1).isEqualTo(mPreqRegistEvaluation2);
        mPreqRegistEvaluation2.setId(2L);
        assertThat(mPreqRegistEvaluation1).isNotEqualTo(mPreqRegistEvaluation2);
        mPreqRegistEvaluation1.setId(null);
        assertThat(mPreqRegistEvaluation1).isNotEqualTo(mPreqRegistEvaluation2);
    }
}
