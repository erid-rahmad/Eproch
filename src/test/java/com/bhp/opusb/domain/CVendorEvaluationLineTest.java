package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CVendorEvaluationLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CVendorEvaluationLine.class);
        CVendorEvaluationLine cVendorEvaluationLine1 = new CVendorEvaluationLine();
        cVendorEvaluationLine1.setId(1L);
        CVendorEvaluationLine cVendorEvaluationLine2 = new CVendorEvaluationLine();
        cVendorEvaluationLine2.setId(cVendorEvaluationLine1.getId());
        assertThat(cVendorEvaluationLine1).isEqualTo(cVendorEvaluationLine2);
        cVendorEvaluationLine2.setId(2L);
        assertThat(cVendorEvaluationLine1).isNotEqualTo(cVendorEvaluationLine2);
        cVendorEvaluationLine1.setId(null);
        assertThat(cVendorEvaluationLine1).isNotEqualTo(cVendorEvaluationLine2);
    }
}
